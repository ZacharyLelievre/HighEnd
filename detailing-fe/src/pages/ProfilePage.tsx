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
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;

  const { getAccessTokenSilently, user } = useAuth0();
  const navigate = useNavigate();

  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [userType, setUserType] = useState<"Customer" | "Employee" | null>(
    null,
  );
  const [rescheduleModal, setRescheduleModal] = useState<{
    open: boolean;
    appointmentId: string | null;
  }>({
    open: false,
    appointmentId: null,
  });
  const [newDate, setNewDate] = useState<string>("");
  const [newStartTime, setNewStartTime] = useState<string>("");
  const [newEndTime, setNewEndTime] = useState<string>("");

  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [appointments, setAppointments] = useState<AppointmentModel[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editedProfile, setEditedProfile] = useState<
    CustomerModel | EmployeeModel | null
  >(null);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        setLoading(true);
        const token = await getAccessTokenSilently();

        try {
          const customerResponse = await axios.get<CustomerModel>(
            `${apiBaseUrl}/customers/me`,
            {
              headers: { Authorization: `Bearer ${token}` },
            },
          );
          setProfile(customerResponse.data);
          setUserType("Customer");
        } catch (customerError: any) {
          if (customerError.response && customerError.response.status === 404) {
            try {
              const employeeResponse = await axios.get<EmployeeModel>(
                `${apiBaseUrl}/employees/me`,
                {
                  headers: { Authorization: `Bearer ${token}` },
                },
              );
              setProfile(employeeResponse.data);
              setUserType("Employee");
            } catch (employeeError: any) {
              if (
                employeeError.response &&
                employeeError.response.status === 404
              ) {
                setError(
                  "No profile information found for the authenticated user.",
                );
              } else {
                setError("Error fetching employee profile.");
                console.error(
                  "Error fetching employee profile:",
                  employeeError,
                );
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

  useEffect(() => {
    const fetchAppointments = async () => {
      try {
        if (!profile || !userType) return;
        const token = await getAccessTokenSilently();

        if (userType === "Employee") {
          const emp = profile as EmployeeModel;
          if (!emp.employeeId) return;

          const response = await axios.get<AppointmentModel[]>(
            `${apiBaseUrl}/appointments/employee/${emp.employeeId}`,
            {
              headers: { Authorization: `Bearer ${token}` },
            },
          );
          setAppointments(response.data);
        } else if (userType === "Customer") {
          const cus = profile as CustomerModel;
          if (!cus.customerId) return;

          const response = await axios.get<AppointmentModel[]>(
            `${apiBaseUrl}/appointments/customer/${cus.customerId}`,
            {
              headers: { Authorization: `Bearer ${token}` },
            },
          );
          setAppointments(response.data);
        }
      } catch (err) {
        console.error("Error fetching appointments:", err);
      }
    };

    fetchAppointments();
  }, [profile, userType, getAccessTokenSilently]);

  const handleCancelAppointment = async (appointmentId: string) => {
    if (window.confirm("Are you sure you want to cancel this appointment?")) {
      try {
        const token = await getAccessTokenSilently();
        await axios.delete(`${apiBaseUrl}/appointments/${appointmentId}`, {
          headers: { Authorization: `Bearer ${token}` },
        });

        setAppointments((prevAppointments) =>
          prevAppointments.filter(
            (appt) => appt.appointmentId !== appointmentId,
          ),
        );
      } catch (error) {
        console.error("Error canceling appointment:", error);
      }
    }
  };

  const openModal = () => {
    setEditedProfile(profile);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (editedProfile) {
      setEditedProfile({ ...editedProfile, [e.target.name]: e.target.value });
    }
  };

  const handleSaveChanges = async () => {
    try {
      const token = await getAccessTokenSilently();

      if (userType === "Customer") {
        const cus = profile as CustomerModel;
        await axios.put(
          `${apiBaseUrl}/customers/${cus.customerId}`,
          editedProfile,
          { headers: { Authorization: `Bearer ${token}` } },
        );
      } else if (userType === "Employee") {
        const emp = profile as EmployeeModel;
        await axios.put(
          `${apiBaseUrl}/employees/${emp.employeeId}`,
          editedProfile,
          { headers: { Authorization: `Bearer ${token}` } },
        );
      }

      setProfile(editedProfile);
      closeModal();
    } catch (error) {
      console.error("Error updating profile:", error);
    }
  };

  const handleAppointmentClick = (appointmentId: string) => {
    navigate(`/my-appointments/${appointmentId}`);
  };

  const openRescheduleModal = (appointmentId: string) => {
    setRescheduleModal({ open: true, appointmentId });
  };

  const closeRescheduleModal = () => {
    setRescheduleModal({ open: false, appointmentId: null });
  };

  const handleReschedule = async () => {
    if (!rescheduleModal.appointmentId) return;

    try {
      const token = await getAccessTokenSilently();
      await axios.put(
        `${apiBaseUrl}/appointments/${rescheduleModal.appointmentId}/reschedule`,
        {
          newDate,
          newStartTime,
          newEndTime,
        },
        { headers: { Authorization: `Bearer ${token}` } },
      );

      // Refresh appointments after rescheduling
      setAppointments((prev) =>
        prev.map((appt) =>
          appt.appointmentId === rescheduleModal.appointmentId
            ? {
                ...appt,
                appointmentDate: newDate,
                appointmentTime: newStartTime,
              }
            : appt,
        ),
      );
      closeRescheduleModal();
    } catch (error) {
      console.error("Error rescheduling appointment:", error);
    }
  };

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
            <div className="profile-header">
              <div className="profile-avatar">
                <img
                  src={
                    user?.picture ||
                    (userType === "Employee" &&
                    (profile as EmployeeModel).imagePath
                      ? `/${(profile as EmployeeModel).imagePath}`
                      : "https://via.placeholder.com/100")
                  }
                  alt={user?.name || ""}
                />
              </div>
              <div className="profile-name">
                <h2>
                  {userType === "Customer"
                    ? `${(profile as CustomerModel).customerFirstName} ${(profile as CustomerModel).customerLastName}`
                    : `${(profile as EmployeeModel).first_name} ${(profile as EmployeeModel).last_name}`}
                </h2>
                <p className="profile-email">
                  {userType === "Customer"
                    ? (profile as CustomerModel).customerEmailAddress
                    : (profile as EmployeeModel).email}
                </p>
              </div>
              <button className="edit-button" onClick={openModal}>
                Edit Profile
              </button>
            </div>

            {isModalOpen && (
              <div className="modal-overlay">
                <div className="modal">
                  <h3>Edit Profile</h3>
                  {userType === "Customer" ? (
                    <>
                      <input
                        type="text"
                        name="customerFirstName"
                        value={
                          editedProfile && userType === "Customer"
                            ? (editedProfile as CustomerModel).customerFirstName
                            : ""
                        }
                        onChange={handleInputChange}
                        placeholder="First Name"
                      />
                      <input
                        type="text"
                        name="customerLastName"
                        value={
                          editedProfile && userType === "Customer"
                            ? (editedProfile as CustomerModel).customerLastName
                            : ""
                        }
                        onChange={handleInputChange}
                        placeholder="Last Name"
                      />
                      <input
                        type="text"
                        name="streetAddress"
                        value={
                          editedProfile && userType === "Customer"
                            ? (editedProfile as CustomerModel).streetAddress
                            : ""
                        }
                        onChange={handleInputChange}
                        placeholder="Street Address"
                      />
                      <input
                        type="text"
                        name="city"
                        value={
                          editedProfile && userType === "Customer"
                            ? (editedProfile as CustomerModel).city
                            : ""
                        }
                        onChange={handleInputChange}
                        placeholder="City"
                      />
                      <input
                        type="text"
                        name="postalCode"
                        value={
                          editedProfile && userType === "Customer"
                            ? (editedProfile as CustomerModel).postalCode
                            : ""
                        }
                        onChange={handleInputChange}
                        placeholder="Postal Code"
                      />
                      <input
                        type="text"
                        name="province"
                        value={
                          editedProfile && userType === "Customer"
                            ? (editedProfile as CustomerModel).province
                            : ""
                        }
                        onChange={handleInputChange}
                        placeholder="Province"
                      />
                      <input
                        type="text"
                        name="country"
                        value={
                          editedProfile && userType === "Customer"
                            ? (editedProfile as CustomerModel).country
                            : ""
                        }
                        onChange={handleInputChange}
                        placeholder="Country"
                      />
                    </>
                  ) : (
                    <>
                      <input
                        type="text"
                        name="first_name"
                        value={
                          editedProfile && userType === "Employee"
                            ? (editedProfile as EmployeeModel).first_name
                            : ""
                        }
                        onChange={handleInputChange}
                        placeholder="First Name"
                      />
                      <input
                        type="text"
                        name="last_name"
                        value={
                          editedProfile && userType === "Employee"
                            ? (editedProfile as EmployeeModel).last_name
                            : ""
                        }
                        onChange={handleInputChange}
                        placeholder="Last Name"
                      />
                      <input
                        type="text"
                        name="position"
                        value={
                          editedProfile && userType === "Employee"
                            ? (editedProfile as EmployeeModel).position
                            : ""
                        }
                        onChange={handleInputChange}
                        placeholder="Position"
                      />
                      <input
                        type="text"
                        name="phone"
                        value={
                          editedProfile && userType === "Employee"
                            ? (editedProfile as EmployeeModel).phone
                            : ""
                        }
                        onChange={handleInputChange}
                        placeholder="Phone Number"
                      />
                    </>
                  )}

                  <button onClick={handleSaveChanges}>Save Changes</button>
                  <button onClick={closeModal}>Cancel</button>
                </div>
              </div>
            )}

            <div className="appointments-section">
              <h3>My Appointments</h3>
              {appointments.length === 0 ? (
                <p>No appointments assigned.</p>
              ) : (
                <ul className="appointments-list">
                  {appointments.map((appt) => (
                    <li key={appt.appointmentId} className="appointment-item">
                      <span className="appointment-service">
                        {appt.serviceName}
                      </span>
                      <span className="appointment-date">
                        {new Date(appt.appointmentDate).toLocaleDateString()}
                      </span>
                      <span className="appointment-status">{appt.status}</span>
                      <button
                        className="cancel-button"
                        onClick={() =>
                          handleCancelAppointment(appt.appointmentId)
                        }
                      >
                        Cancel Appointment
                      </button>
                      <button
                        onClick={() => openRescheduleModal(appt.appointmentId)}
                      >
                        Reschedule
                      </button>
                    </li>
                  ))}
                </ul>
              )}
            </div>
            {rescheduleModal.open && (
              <div className="modal-overlay">
                <div className="modal">
                  <h3>Reschedule Appointment</h3>
                  <input
                    type="date"
                    value={newDate}
                    onChange={(e) => setNewDate(e.target.value)}
                    placeholder="New Date"
                  />
                  <input
                    type="time"
                    value={newStartTime}
                    onChange={(e) => setNewStartTime(e.target.value)}
                    placeholder="New Start Time"
                  />
                  <input
                    type="time"
                    value={newEndTime}
                    onChange={(e) => setNewEndTime(e.target.value)}
                    placeholder="New End Time"
                  />
                  <button onClick={handleReschedule}>Confirm Reschedule</button>
                  <button onClick={closeRescheduleModal}>Cancel</button>
                </div>
              </div>
            )}

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
                    <span>Postal Code:</span>
                    <span>{(profile as CustomerModel).postalCode}</span>
                  </div>
                  <div className="detail-row">
                    <span>Province:</span>
                    <span>{(profile as CustomerModel).province}</span>
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
                    <span>Phone:</span>
                    <span>{(profile as EmployeeModel).phone}</span>
                  </div>
                </>
              )}
            </div>
          </div>
        ) : (
          <div className="profile-card">
            <p>No profile information available.</p>
          </div>
        )}
      </div>
    </div>
  );
}
