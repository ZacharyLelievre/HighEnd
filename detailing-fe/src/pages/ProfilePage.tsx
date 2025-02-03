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
  const [userType, setUserType] = useState<"Customer" | "Employee" | null>(
    null,
  );
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [appointments, setAppointments] = useState<AppointmentModel[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editedProfile, setEditedProfile] = useState<CustomerModel | null>(
    null,
  );

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        setLoading(true);
        const token = await getAccessTokenSilently();

        try {
          const customerResponse = await axios.get<CustomerModel>(
            "https://highend-zke6.onrender.com/api/customers/me",
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
                "https://highend-zke6.onrender.com/api/employees/me",
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

  // Fetch appointments for employees
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
            headers: { Authorization: `Bearer ${token}` },
          },
        );
        setAppointments(response.data);
      } catch (err) {
        console.error("Error fetching employee's appointments:", err);
      }
    };

    fetchEmployeeAppointments();
  }, [userType, profile, getAccessTokenSilently]);

  // Fetch appointments for customers
  useEffect(() => {
    const fetchCustomerAppointments = async () => {
      try {
        if (userType !== "Customer" || !profile) return;
        const token = await getAccessTokenSilently();

        const cus = profile as CustomerModel;
        if (!cus.customerId) return;

        const response = await axios.get<AppointmentModel[]>(
          `https://highend-zke6.onrender.com/api/appointments/customer/${cus.customerId}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          },
        );
        setAppointments(response.data);
      } catch (err) {
        console.error("Error fetching customer's appointments:", err);
      }
    };

    fetchCustomerAppointments();
  }, [userType, profile, getAccessTokenSilently]);

  const handleAppointmentClick = (appointmentId: string) => {
    navigate(`/my-appointments/${appointmentId}`);
  };

  const openModal = () => {
    setEditedProfile(profile as CustomerModel);
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
      const cus = profile as CustomerModel;
      await axios.put(
        `https://highend-zke6.onrender.com/api/customers/${cus.customerId}`,
        editedProfile,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      setProfile(editedProfile);
      closeModal();
    } catch (error) {
      console.error("Error updating customer profile:", error);
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
                />
              </div>
              <div className="profile-name">
                <h2>
                  {userType === "Customer"
                    ? `${(profile as CustomerModel).customerFirstName} ${
                        (profile as CustomerModel).customerLastName
                      }`
                    : `${(profile as EmployeeModel).first_name} ${
                        (profile as EmployeeModel).last_name
                      }`}
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
                <div className="modal">{/* Modal content here */}</div>
              </div>
            )}

            {/* Appointments Section */}
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
                      onClick={() => handleAppointmentClick(appt.appointmentId)}
                    >
                      <span className="appointment-service">
                        {appt.serviceName}
                      </span>
                      <span className="appointment-date">
                        {new Date(appt.appointmentDate).toLocaleDateString()}
                      </span>
                      <span className="appointment-status">{appt.status}</span>
                    </li>
                  ))}
                </ul>
              )}
            </div>

            {/* Profile Details */}
            <div className="profile-details">
              {userType === "Customer" ? (
                <>
                  <div className="detail-row">
                    <span>Street Address:</span>
                    <span>{(profile as CustomerModel).streetAddress}</span>
                  </div>
                  {/* Other customer details */}
                </>
              ) : (
                <>
                  <div className="detail-row">
                    <span>First Name:</span>
                    <span>{(profile as EmployeeModel).first_name}</span>
                  </div>
                  {/* Other employee details */}
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
