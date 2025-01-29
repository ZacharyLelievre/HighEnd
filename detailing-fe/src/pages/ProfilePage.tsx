import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import axios from "axios";
import { NavBar } from "../nav/NavBar";
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

// Minimal shape for your appointments
interface AppointmentModel {
  appointmentId: string;
  serviceName: string;
  appointmentDate: string;
  appointmentTime: string;
  status: string;
  // ... add any other fields you need
}

const ALL_STATUSES = [
  "PENDING",
  "CONFIRMED",
  "IN_PROGRESS",
  "COMPLETED",
  "REJECTED",
  "CANCELLED",
];

export function ProfilePage() {
  const { getAccessTokenSilently, user } = useAuth0();

  const [profile, setProfile] = useState<CustomerInfo | null>(null);
  const [loadingProfile, setLoadingProfile] = useState<boolean>(true);

  // --- Employee appointments logic ---
  const [appointments, setAppointments] = useState<AppointmentModel[]>([]);
  const [selectedAppointment, setSelectedAppointment] =
      useState<AppointmentModel | null>(null);

  useEffect(() => {
    // Fetch customer profile (already in your code)
    const fetchProfile = async () => {
      try {
        setLoadingProfile(true);
        const token = await getAccessTokenSilently();
        const response = await axios.get<CustomerInfo>(
            "https://highend-zke6.onrender.com/api/customers/me",
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
        );
        setProfile(response.data);
      } catch (error) {
        console.error("Error fetching profile:", error);
      } finally {
        setLoadingProfile(false);
      }
    };
    fetchProfile();
  }, [getAccessTokenSilently]);

  // (Optional) If you want to see the user's sub in console for debugging
  // console.log("Auth0 user sub:", user?.sub);

  // Fetch employee appointments if user has a sub (Auth0 user)
  useEffect(() => {
    if (!user?.sub) return; // no user ID? skip

    // Call GET /api/appointments/employee/{employeeId}
    // We assume user.sub is the "employeeId" in your DB
    const fetchAppointmentsForEmployee = async () => {
      try {
        const token = await getAccessTokenSilently();
        const res = await axios.get<AppointmentModel[]>(
            `https://localhost:8080/api/appointments/employee/${user.sub}`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
        );
        setAppointments(res.data);
      } catch (err) {
        console.error("Error fetching employee appointments:", err);
        // If 404 or no appointments, you might just set an empty array
        setAppointments([]);
      }
    };

    fetchAppointmentsForEmployee();
  }, [user?.sub, getAccessTokenSilently]);

  // Handler: select an appointment to view details
  const handleSelectAppointment = (appt: AppointmentModel) => {
    setSelectedAppointment(appt);
  };

  // Handler: change status
  const handleChangeStatus = async (appointmentId: string, newStatus: string) => {
    try {
      const token = await getAccessTokenSilently();
      await axios.put(
          `https://highend-zke6.onrender.com/api/appointments/${appointmentId}/status`,
          { status: newStatus },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
      );
      // Update local state
      setAppointments((prev) =>
          prev.map((appt) =>
              appt.appointmentId === appointmentId
                  ? { ...appt, status: newStatus }
                  : appt
          )
      );
      // Update selected
      if (selectedAppointment && selectedAppointment.appointmentId === appointmentId) {
        setSelectedAppointment({ ...selectedAppointment, status: newStatus });
      }
    } catch (err) {
      console.error("Error updating appointment status:", err);
    }
  };

  if (loadingProfile) {
    return (
        <div>
          <NavBar />
          <div className="profile-container">
            <p>Loading your profile...</p>
          </div>
        </div>
    );
  }

  // If there's no customer info, just show a fallback
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

  // Render Profile Page
  return (
      <div>
        <NavBar />
        <div className="profile-container">
          {/* Customer Info */}
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

          {/* Employee Appointments */}
          <div style={{ marginTop: "2rem" }}>
            <h3>My Assigned Appointments</h3>
            {appointments.length === 0 && (
                <p>No appointments assigned.</p>
            )}

            {appointments.map((appt) => (
                <div key={appt.appointmentId} style={{ marginBottom: "1rem" }}>
                  <button onClick={() => handleSelectAppointment(appt)}>
                    {appt.serviceName} – {appt.appointmentDate} at {appt.appointmentTime} {" "}
                    (Status: {appt.status})
                  </button>
                </div>
            ))}

            {/* If we clicked on an appointment, show details + status dropdown */}
            {selectedAppointment && (
                <div style={{ marginTop: "1rem" }}>
                  <h4>Appointment Details</h4>
                  <p><strong>ID:</strong> {selectedAppointment.appointmentId}</p>
                  <p><strong>Service:</strong> {selectedAppointment.serviceName}</p>
                  <p>
                    <strong>Date/Time:</strong>{" "}
                    {selectedAppointment.appointmentDate}{" "}
                    {selectedAppointment.appointmentTime}
                  </p>
                  <p><strong>Status:</strong> {selectedAppointment.status}</p>

                  <div style={{ marginTop: "8px" }}>
                    <label htmlFor="statusSelect"><strong>Change Status:</strong>{" "}</label>
                    <select
                        id="statusSelect"
                        value={selectedAppointment.status}
                        onChange={(e) =>
                            handleChangeStatus(
                                selectedAppointment.appointmentId,
                                e.target.value
                            )
                        }
                    >
                      {ALL_STATUSES.map((st) => (
                          <option key={st} value={st}>
                            {st}
                          </option>
                      ))}
                    </select>
                  </div>
                </div>
            )}
          </div>
        </div>
      </div>
  );
}