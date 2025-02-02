// src/models/ProfilePage.tsx

import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import axios from "axios";
import { NavBar } from "../nav/NavBar";
import "./ProfilePage.css";
import { CustomerModel } from "../models/dtos/CustomerModel";
import { EmployeeModel } from "../models/dtos/EmployeeModel";
import { AppointmentModel } from "../models/dtos/AppointmentModel";
import { useNavigate } from "react-router-dom";

type UserProfile = CustomerModel | EmployeeModel;

export function ProfilePage() {
  const { getAccessTokenSilently, user } = useAuth0();
  const navigate = useNavigate();

  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [userType, setUserType] = useState<"Customer" | "Employee" | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [appointments, setAppointments] = useState<AppointmentModel[]>([]);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        setLoading(true);
        const token = await getAccessTokenSilently();

        // Attempt to fetch Customer profile
        try {
          const customerResponse = await axios.get<CustomerModel>(
              "http://localhost:8080/api/customers/me",
              {
                headers: {
                  Authorization: `Bearer ${token}`,
                },
              },
          );
          setProfile(customerResponse.data);
          setUserType("Customer");

          // Fetch appointments for the customer
          const appointmentsResponse = await axios.get<AppointmentModel[]>(
              `http://localhost:8080/api/appointments/customer/${customerResponse.data.customerId}`,
              {
                headers: {
                  Authorization: `Bearer ${token}`,
                },
              },
          );
          setAppointments(appointmentsResponse.data);
        } catch (customerError: any) {
          if (customerError.response && customerError.response.status === 404) {
            // If Customer not found, attempt to fetch Employee profile
            try {
              const employeeResponse = await axios.get<EmployeeModel>(
                  "http://localhost:8080/api/employees/me",
                  {
                    headers: {
                      Authorization: `Bearer ${token}`,
                    },
                  },
              );
              setProfile(employeeResponse.data);
              setUserType("Employee");

              // Fetch appointments for the employee
              const emp = employeeResponse.data;
              if (emp.employeeId) {
                const response = await axios.get<AppointmentModel[]>(
                    `http://localhost:8080/api/appointments/employee/${emp.employeeId}`,
                    {
                      headers: {
                        Authorization: `Bearer ${token}`,
                      },
                    },
                );
                setAppointments(response.data);
              }
            } catch (employeeError: any) {
              if (
                  employeeError.response &&
                  employeeError.response.status === 404
              ) {
                setError("No profile information found for the authenticated user.");
              } else {
                setError("Error fetching employee profile.");
                console.error("Error fetching employee profile:", employeeError);
              }
            }
          } else {
            setError("Error fetching customer profile.");
            console.error("Error fetching customer profile:", customerError);
          }
        }
      } catch (err) {
        setError("An unexpected error occurred while fetching the profile.");
        console.error("Unexpected error:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, [getAccessTokenSilently]);

<<<<<<< HEAD
  // Loading State
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

  // Error State
  if (error) {
    return (
        <div>
          <NavBar />
          <div className="profile-container">
            <p>{error}</p>
          </div>
        </div>
    );
  }

  // Profile Not Found
  if (!profile || !userType) {
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
          <div className="profile-card">
            <div className="profile-header">
              <div className="profile-avatar">
                <img
                    src={
                        user?.picture ||
                        (userType === "Employee" &&
                        (profile as EmployeeModel).imagePath
                            ? `https://highend-zke6.onrender.com/${
                                (profile as EmployeeModel).imagePath
                            }`
                            : "https://via.placeholder.com/100")
                    }
                    alt={
                      userType === "Customer"
                          ? `${(profile as CustomerModel).customerFirstName} ${
                              (profile as CustomerModel).customerLastName
                          }`
                          : `${(profile as EmployeeModel).first_name} ${
                              (profile as EmployeeModel).last_name
                          }`
                    }
=======
  // Once we know it’s an Employee, fetch their appointments
  useEffect(() => {
    const fetchEmployeeAppointments = async () => {
      try {
        if (userType !== "Employee" || !profile) return;
        const token = await getAccessTokenSilently();

        const emp = profile as EmployeeModel;
        if (!emp.employeeId) return;

        const response = await axios.get<AppointmentModel[]>(
          `https://highend-zke6.onrender.com/api/appointments/employee/${emp.employeeId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          },
        );
        setAppointments(response.data);
      } catch (err) {
        console.error("Error fetching employee's appointments:", err);
      }
    };

    fetchEmployeeAppointments();
  }, [userType, profile, getAccessTokenSilently]);

  // Click handler to navigate to an appointment details page
  function handleAppointmentClick(appointmentId: string) {
    navigate(`/my-appointments/${appointmentId}`);
  }

  return (
    <div className="profile-page">
      <NavBar />
      <div className="profile-container">
        {loading ? (
          <div className="profile-card">
            <p>Loading your profile...</p>
          </div>
        ) : error ? (
          <div className="profile-card">
            <p className="error-message">{error}</p>
          </div>
        ) : profile && userType ? (
          <div className="profile-card">
            {/* Profile Header */}
            <div className="profile-header">
              <div className="profile-avatar">
                <img
                  src={
                    user?.picture ||
                    (userType === "Employee" &&
                    (profile as EmployeeModel).imagePath
                      ? `https://highend-zke6.onrender.com/${
                          (profile as EmployeeModel).imagePath
                        }`
                      : "https://via.placeholder.com/100")
                  }
                  alt={
                    userType === "Customer"
                      ? `${(profile as CustomerModel).customerFirstName} ${
                          (profile as CustomerModel).customerLastName
                        }`
                      : `${(profile as EmployeeModel).first_name} ${
                          (profile as EmployeeModel).last_name
                        }`
                  }
>>>>>>> 63a463be5055505801441a57899eef4c8852bb42
                />
              </div>
              <div className="profile-name">
                <h2>
                  {userType === "Customer"
<<<<<<< HEAD
                      ? `${(profile as CustomerModel).customerFirstName} ${
                          (profile as CustomerModel).customerLastName
                      }`
                      : `${(profile as EmployeeModel).first_name} ${
                          (profile as EmployeeModel).last_name
=======
                    ? `${(profile as CustomerModel).customerFirstName} ${
                        (profile as CustomerModel).customerLastName
                      }`
                    : `${(profile as EmployeeModel).first_name} ${
                        (profile as EmployeeModel).last_name
>>>>>>> 63a463be5055505801441a57899eef4c8852bb42
                      }`}
                </h2>
                <p className="profile-email">
                  {userType === "Customer"
<<<<<<< HEAD
                      ? (profile as CustomerModel).customerEmailAddress
                      : (profile as EmployeeModel).email}
                </p>
              </div>
            </div>

            {/* Display Appointments */}
            <div style={{ marginBottom: "20px", textAlign: "left" }}>
              <h3>My Appointments</h3>
              {appointments.length === 0 ? (
                  <p>No appointments found.</p>
              ) : (
                  <ul>
                    {appointments.map((appt) => (
                        <li key={appt.appointmentId}>
                          <b>{appt.serviceName}</b> on {appt.appointmentDate} (status: {appt.status})
                        </li>
                    ))}
                  </ul>
              )}
            </div>

            {/* Additional profile details */}
            <div className="profile-details">
              {userType === "Customer" && (
                  <>
                    <div className="detail-row">
                      <span>Street Address:</span>{" "}
                      {(profile as CustomerModel).streetAddress}
                    </div>
                    <div className="detail-row">
                      <span>City:</span> {(profile as CustomerModel).city}
                    </div>
                    <div className="detail-row">
                      <span>Province:</span> {(profile as CustomerModel).province}
                    </div>
                    <div className="detail-row">
                      <span>Postal Code:</span>{" "}
                      {(profile as CustomerModel).postalCode}
                    </div>
                    <div className="detail-row">
                      <span>Country:</span> {(profile as CustomerModel).country}
                    </div>
                  </>
              )}

              {userType === "Employee" && (
                  <>
                    <div className="detail-row">
                      <span>First Name:</span>{" "}
                      {(profile as EmployeeModel).first_name}
                    </div>
                    <div className="detail-row">
                      <span>Last Name:</span> {(profile as EmployeeModel).last_name}
                    </div>
                    <div className="detail-row">
                      <span>Position:</span> {(profile as EmployeeModel).position}
                    </div>
                    <div className="detail-row">
                      <span>Email:</span> {(profile as EmployeeModel).email}
                    </div>
                    <div className="detail-row">
                      <span>Phone:</span> {(profile as EmployeeModel).phone}
                    </div>
                  </>
              )}
            </div>
=======
                    ? (profile as CustomerModel).customerEmailAddress
                    : (profile as EmployeeModel).email}
                </p>
              </div>
            </div>

            {/* Appointments Section for Employees */}
            {userType === "Employee" && (
              <div className="appointments-section">
                <h3>My Appointments</h3>
                {appointments.length === 0 ? (
                  <p>No appointments assigned.</p>
                ) : (
                  <ul className="appointments-list">
                    {appointments.map((appt) => (
                      <li
                        key={appt.appointmentId}
                        className="appointment-item"
                        onClick={() =>
                          handleAppointmentClick(appt.appointmentId)
                        }
                      >
                        <span className="appointment-service">
                          {appt.serviceName}
                        </span>
                        <span className="appointment-date">
                          {new Date(appt.appointmentDate).toLocaleDateString()}
                        </span>
                        <span className="appointment-status">
                          {appt.status}
                        </span>
                      </li>
                    ))}
                  </ul>
                )}
              </div>
            )}

            {/* Profile Details */}
            <div className="profile-details">
              {userType === "Customer" ? (
                <>
                  <div className="detail-row">
                    <span>Street Address:</span>
                    <span>{(profile as CustomerModel).streetAddress}</span>
                  </div>
                  <div className="detail-row">
                    <span>City:</span>
                    <span>{(profile as CustomerModel).city}</span>
                  </div>
                  <div className="detail-row">
                    <span>Province:</span>
                    <span>{(profile as CustomerModel).province}</span>
                  </div>
                  <div className="detail-row">
                    <span>Postal Code:</span>
                    <span>{(profile as CustomerModel).postalCode}</span>
                  </div>
                  <div className="detail-row">
                    <span>Country:</span>
                    <span>{(profile as CustomerModel).country}</span>
                  </div>
                </>
              ) : (
                <>
                  <div className="detail-row">
                    <span>First Name:</span>
                    <span>{(profile as EmployeeModel).first_name}</span>
                  </div>
                  <div className="detail-row">
                    <span>Last Name:</span>
                    <span>{(profile as EmployeeModel).last_name}</span>
                  </div>
                  <div className="detail-row">
                    <span>Position:</span>
                    <span>{(profile as EmployeeModel).position}</span>
                  </div>
                  <div className="detail-row">
                    <span>Email:</span>
                    <span>{(profile as EmployeeModel).email}</span>
                  </div>
                  <div className="detail-row">
                    <span>Phone:</span>
                    <span>{(profile as EmployeeModel).phone}</span>
                  </div>
                  <div className="detail-row">
                    <span>Availability:</span>
                    <ul className="availability-list">
                      {(profile as EmployeeModel).availability.map(
                        (avail, index) => (
                          <li key={index}>
                            {avail.dayOfWeek}: {avail.startTime} -{" "}
                            {avail.endTime}
                          </li>
                        ),
                      )}
                    </ul>
                  </div>
                </>
              )}
            </div>
>>>>>>> 63a463be5055505801441a57899eef4c8852bb42
          </div>
        ) : (
          <div className="profile-card">
            <p>No profile information available.</p>
          </div>
        )}
      </div>
  );
}