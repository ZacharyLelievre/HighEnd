// File: detailing-fe/src/pages/ProfilePage.tsx

import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import axios from "axios";
import { NavBar } from "../nav/NavBar";
import { getAppointmentsByEmployeeId } from "../apis/getAppointmentsByEmployeeId";
import { AppointmentModel } from "../models/dtos/AppointmentModel";
import { useNavigate } from "react-router-dom";
import "./ProfilePage.css";

interface CustomerInfo {
  customerFirstName: string;
  customerLastName: string;
  customerEmailAddress: string;
  streetAddress: string;
  city: string;
  province: string;
  postalCode: string;
  country: string;
}

export function ProfilePage() {
  const { getAccessTokenSilently, user } = useAuth0();
  const [profile, setProfile] = useState<CustomerInfo | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [employeeAppointments, setEmployeeAppointments] = useState<AppointmentModel[]>([]);
  const navigate = useNavigate();

  // Detect if the user has the "EMPLOYEES" role
  const isEmployee = user?.["https://highenddetailing/roles"]?.includes("EMPLOYEES");

  // Assuming the employee ID is stored in a custom claim
  const employeeId = user?.["https://highenddetailing/employeeId"];

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        setLoading(true);
        const token = await getAccessTokenSilently();
        const response = await axios.get<CustomerInfo>(
            "https://highend-zke6.onrender.com/api/customers/me",
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            },
        );
        setProfile(response.data);
      } catch (error) {
        console.error("Error fetching profile:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, [getAccessTokenSilently]);

  useEffect(() => {
    // If the user is an employee and has a valid employeeId, fetch appointments
    if (isEmployee && employeeId) {
      const fetchAppointments = async () => {
        try {
          const data = await getAppointmentsByEmployeeId(employeeId);
          // Optionally, sort appointments by date and time and take the top 3
          const sortedAppointments = data
              .sort((a, b) => {
                const dateTimeA = new Date(`${a.appointmentDate}T${a.appointmentTime}`);
                const dateTimeB = new Date(`${b.appointmentDate}T${b.appointmentTime}`);
                return dateTimeA.getTime() - dateTimeB.getTime();
              })
              .slice(0, 3);
          setEmployeeAppointments(sortedAppointments);
        } catch (error) {
          console.error("Error fetching employee appointments:", error);
        }
      };

      fetchAppointments();
    }
  }, [isEmployee, employeeId]);

  if (loading) {
    return (
        <div>
          <NavBar />
          <div className="profile-container">
            <p>Loading your profile...</p>
          </div>
        </div>
    );
  }

  if (!profile) {
    return (
        <div>
          <NavBar />
          <div className="profile-container">
            <p>No profile information available.</p>
          </div>
        </div>
    );
  }

  return (
      <div>
        <NavBar />
        <div className="profile-container">
          {/* Customer Profile Card */}
          <div className="profile-card">
            <div className="profile-header">
              <div className="profile-avatar">
                <img
                    src={user?.picture || "https://via.placeholder.com/100"}
                    alt={`${profile.customerFirstName} ${profile.customerLastName}`}
                />
              </div>
              <div className="profile-name">
                <h2>{`${profile.customerFirstName} ${profile.customerLastName}`}</h2>
                <p className="profile-email">{profile.customerEmailAddress}</p>
              </div>
            </div>
            <div className="profile-details">
              <div className="detail-row">
                <span>Street Address:</span> {profile.streetAddress}
              </div>
              <div className="detail-row">
                <span>City:</span> {profile.city}
              </div>
              <div className="detail-row">
                <span>Province:</span> {profile.province}
              </div>
              <div className="detail-row">
                <span>Postal Code:</span> {profile.postalCode}
              </div>
              <div className="detail-row">
                <span>Country:</span> {profile.country}
              </div>
            </div>
          </div>

          {/* Employee Appointments Section */}
          {isEmployee && (
              <div className="employee-appointments-section">
                <h3>Your Assigned Appointments</h3>
                {employeeAppointments.length === 0 ? (
                    <p>No upcoming appointments.</p>
                ) : (
                    <div className="appointments-container">
                      {employeeAppointments.map((appt) => (
                          <div key={appt.appointmentId} className="appointment-box">
                            <img
                                className="appointment-image"
                                src={`https://highend-zke6.onrender.com/${appt.imagePath}`}
                                alt={appt.serviceName}
                            />
                            <div className="appointment-details">
                              <p>
                                <strong>Service:</strong> {appt.serviceName}
                              </p>
                              <p>
                                <strong>Date:</strong> {appt.appointmentDate}
                              </p>
                              <p>
                                <strong>Status:</strong> {appt.status}
                              </p>
                              <div className="button-container">
                                <button
                                    onClick={() => navigate(`/appointments/${appt.appointmentId}`)}
                                >
                                  View
                                </button>
                              </div>
                            </div>
                          </div>
                      ))}
                    </div>
                )}
              </div>
          )}
        </div>
      </div>
  );
}