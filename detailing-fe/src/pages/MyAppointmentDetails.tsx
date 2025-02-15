import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import axiosInstance from "../apis/axiosInstance";
import { AppointmentModel } from "../models/dtos/AppointmentModel";
import { updateAppointmentStatus } from "../apis/updateAppointmentStatus";
import { NavBar } from "../nav/NavBar";
import { useAuth0 } from "@auth0/auth0-react";
import "./MyAppointmentDetails.css";

type UserType = "Customer" | "Employee";

export function MyAppointmentDetails() {
  const { appointmentId } = useParams();
  const { getAccessTokenSilently } = useAuth0();

  // Profile and user type state (similar to ProfilePage)
  const [profile, setProfile] = useState<any>(null);
  const [userType, setUserType] = useState<UserType | null>(null);
  const [loadingProfile, setLoadingProfile] = useState<boolean>(true);
  const [profileError, setProfileError] = useState<string | null>(null);

  // Appointment and customer details state
  const [appointment, setAppointment] = useState<AppointmentModel | null>(null);
  const [newStatus, setNewStatus] = useState("");
  const [customer, setCustomer] = useState<any>(null);

  // Fetch the profile (customer or employee) similar to ProfilePage logic
  useEffect(() => {
    const fetchProfile = async () => {
      try {
        setLoadingProfile(true);
        const token = await getAccessTokenSilently();

        try {
          const customerResponse = await axios.get(
            `${process.env.REACT_APP_API_BASE_URL}/customers/me`,
            { headers: { Authorization: `Bearer ${token}` } },
          );
          setProfile(customerResponse.data);
          setUserType("Customer");
        } catch (customerError: any) {
          if (customerError.response && customerError.response.status === 404) {
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

  // Once the appointment is loaded, fetch the customer's details
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

  // Update status handler (only for employees)
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

  // Render loading state for profile or appointment if necessary
  if (loadingProfile || !appointment) {
    return <div className="loading">Loading...</div>;
  }

  if (profileError) {
    return <div className="loading">{profileError}</div>;
  }

  return (
    <div className="appointment-details-container">
      <NavBar />
      <div className="appointment-details">
        <h2 className="title">Appointment Details</h2>
        {/* Flex container to hold appointment details and customer address */}
        <div className="details-flex">
          <div className="details-grid">
            <div className="detail-item">
              <span className="detail-label">Service:</span>
              <span className="detail-value">{appointment.serviceName}</span>
            </div>
            <div className="detail-item">
              <span className="detail-label">Date:</span>
              <span className="detail-value">
                {appointment.appointmentDate}
              </span>
            </div>
            <div className="detail-item">
              <span className="detail-label">Time:</span>
              <span className="detail-value">
                {appointment.appointmentTime}
              </span>
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

        {/* Only show the update status section if the user is an Employee */}
        {userType === "Employee" && (
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
              <option value="PROGRESS">Progress</option>
              <option value="COMPLETED">Completed</option>
              <option value="CANCELLED">Cancelled</option>
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
