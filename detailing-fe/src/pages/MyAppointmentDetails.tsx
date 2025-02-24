import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import axiosInstance from "../apis/axiosInstance";
import { AppointmentModel } from "../models/dtos/AppointmentModel";
import { updateAppointmentStatus } from "../apis/updateAppointmentStatus";
import { NavBar } from "../nav/NavBar";
import { useAuth0 } from "@auth0/auth0-react";
import "./MyAppointmentDetails.css";

type UserType = "Customer" | "Employee" | "Admin";

export function MyAppointmentDetails() {
  const { appointmentId } = useParams();
  const { getAccessTokenSilently } = useAuth0();
  const navigate = useNavigate();

  // Profile and user type state
  const [profile, setProfile] = useState<any>(null);
  const [userType, setUserType] = useState<UserType | null>(null);
  const [loadingProfile, setLoadingProfile] = useState<boolean>(true);
  const [profileError, setProfileError] = useState<string | null>(null);

  // Appointment and customer details state
  const [appointment, setAppointment] = useState<AppointmentModel | null>(null);
  const [newStatus, setNewStatus] = useState("");
  const [customer, setCustomer] = useState<any>(null);

  // New state to check if the user is admin
  const [isAdmin, setIsAdmin] = useState(false);

  // Fetch profile (admin, customer or employee)
  useEffect(() => {
    const fetchProfile = async () => {
      try {
        setLoadingProfile(true);
        const token = await getAccessTokenSilently();

        // Decode token to check roles
        const base64Url = token.split(".")[1];
        const decodedPayload = atob(base64Url);
        const tokenData = JSON.parse(decodedPayload);
        const roles =
            tokenData["https://highenddetailing/roles"] || tokenData.roles || [];

        if (roles.includes("ADMIN")) {
          setIsAdmin(true);
          setProfile({ role: "ADMIN" });
          setUserType("Admin");
          return; // Skip further profile fetching for admin
        }

        // If not admin, try fetching as Customer first
        try {
          const customerResponse = await axios.get(
              `${process.env.REACT_APP_API_BASE_URL}/customers/me`,
              { headers: { Authorization: `Bearer ${token}` } },
          );
          setProfile(customerResponse.data);
          setUserType("Customer");
        } catch (customerError: any) {
          if (customerError.response && customerError.response.status === 404) {
            // Try fetching as Employee
            try {
              const employeeResponse = await axios.get(
                  `${process.env.REACT_APP_API_BASE_URL}/employees/me`,
                  { headers: { Authorization: `Bearer ${token}` } },
              );
              setProfile(employeeResponse.data);
              setUserType("Employee");
            } catch (employeeError: any) {
              if (
                  employeeError.response &&
                  employeeError.response.status === 404
              ) {
                setProfileError(
                    "No profile information found for the authenticated user.",
                );
              } else {
                setProfileError("Error fetching employee profile.");
                console.error(
                    "Error fetching employee profile:",
                    employeeError,
                );
              }
            }
          } else {
            setProfileError("Error fetching customer profile.");
            console.error("Error fetching customer profile:", customerError);
          }
        }
      } catch (err) {
        setProfileError(
            "An unexpected error occurred while fetching the profile.",
        );
        console.error("Unexpected error:", err);
      } finally {
        setLoadingProfile(false);
      }
    };

    fetchProfile();
  }, [getAccessTokenSilently]);

  // Fetch appointment details
  useEffect(() => {
    const fetchAppointment = async () => {
      try {
        const response = await axiosInstance.get<AppointmentModel>(
            `appointments/${appointmentId}`,
        );
        setAppointment(response.data);
      } catch (error) {
        console.error("Error fetching appointment:", error);
      }
    };

    if (appointmentId) {
      fetchAppointment();
    }
  }, [appointmentId]);

  // Fetch customer details after appointment is loaded
  useEffect(() => {
    const fetchCustomer = async () => {
      try {
        if (!appointment?.customerId) return;
        const token = await getAccessTokenSilently();
        const response = await axiosInstance.get(
            `customers/${appointment.customerId}`,
            { headers: { Authorization: `Bearer ${token}` } },
        );
        setCustomer(response.data);
      } catch (error) {
        console.error("Error fetching customer details:", error);
      }
    };

    if (appointment) {
      fetchCustomer();
    }
  }, [appointment, getAccessTokenSilently]);

  // Determine allowed statuses based on role
  const getStatusOptions = () => {
    if (isAdmin) {
      return [
        "CONFIRMED",
        "REJECTED",
        "CANCELLED",
        "PENDING",
        "PROGRESS",
        "COMPLETED",
      ];
    }
    // For employees
    return ["PROGRESS", "COMPLETED", "CANCELLED"];
  };

  // Update status handler (for Employee or Admin)
  const handleStatusChange = async () => {
    if (!newStatus) {
      alert("Please select a status to update.");
      return;
    }
    try {
      const updated = await updateAppointmentStatus(
          appointment!.appointmentId,
          newStatus,
      );
      setAppointment(updated);
      alert("Status updated successfully!");
      setNewStatus("");
    } catch (err) {
      console.error("Error updating status:", err);
      alert("Failed to update status. Please try again.");
    }
  };

  if (loadingProfile || !appointment) {
    return <div className="loading">Loading...</div>;
  }

  if (profileError) {
    return <div className="loading">{profileError}</div>;
  }

  return (
      <div className="appointment-details-container">
        <NavBar />

        {/* Go Back Button */}
        <div className="back-button-container">
          <button className="back-button" onClick={() => window.history.back()}>
            Go Back
          </button>
        </div>

        <div className="appointment-details">
          <h2 className="title">Appointment Details</h2>
          <div className="details-flex">
            <div className="details-grid">
              <div className="detail-item">
                <span className="detail-label">Service:</span>
                <span className="detail-value">{appointment.serviceName}</span>
              </div>
              <div className="detail-item">
                <span className="detail-label">Date:</span>
                <span className="detail-value">{appointment.appointmentDate}</span>
              </div>
              <div className="detail-item">
                <span className="detail-label">Time:</span>
                <span className="detail-value">{appointment.appointmentTime}</span>
              </div>
              <div className="detail-item">
                <span className="detail-label">Status:</span>
                <span className="detail-value">{appointment.status}</span>
              </div>
              <div className="detail-item">
                <span className="detail-label">Customer:</span>
                <span className="detail-value">{appointment.customerName}</span>
              </div>
            </div>

            <div className="address-details">
              <h3>Customer Address</h3>
              {customer ? (
                  <>
                    <div className="detail-item">
                      <span className="detail-label">Street:</span>
                      <span className="detail-value">{customer.streetAddress}</span>
                    </div>
                    <div className="detail-item">
                      <span className="detail-label">City:</span>
                      <span className="detail-value">{customer.city}</span>
                    </div>
                    <div className="detail-item">
                      <span className="detail-label">Postal Code:</span>
                      <span className="detail-value">{customer.postalCode}</span>
                    </div>
                    <div className="detail-item">
                      <span className="detail-label">Province:</span>
                      <span className="detail-value">{customer.province}</span>
                    </div>
                    <div className="detail-item">
                      <span className="detail-label">Country:</span>
                      <span className="detail-value">{customer.country}</span>
                    </div>
                  </>
              ) : (
                  <p>Loading customer address...</p>
              )}
            </div>
          </div>

          {(userType === "Employee" || isAdmin) && (
              <div className="status-update">
                <label htmlFor="statusSelect" className="status-label">
                  Update Status:
                </label>
                <select
                    id="statusSelect"
                    value={newStatus}
                    onChange={(e) => setNewStatus(e.target.value)}
                    className="status-select"
                >
                  <option value="">--Select--</option>
                  {getStatusOptions().map((status) => (
                      <option key={status} value={status}>
                        {status.charAt(0) + status.slice(1).toLowerCase()}
                      </option>
                  ))}
                </select>
                <button
                    onClick={handleStatusChange}
                    className="save-button"
                    disabled={!newStatus}
                >
                  Save
                </button>
              </div>
          )}
        </div>
      </div>
  );
}