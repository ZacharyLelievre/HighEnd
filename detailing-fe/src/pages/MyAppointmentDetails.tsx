import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosInstance from "../apis/axiosInstance";
import { AppointmentModel } from "../models/dtos/AppointmentModel";
import { updateAppointmentStatus } from "../apis/updateAppointmentStatus";
import { NavBar } from "../nav/NavBar";
import { useAuth0 } from "@auth0/auth0-react";
import "./MyAppointmentDetails.css";

export function MyAppointmentDetails() {
  const { appointmentId } = useParams();
  const { getAccessTokenSilently } = useAuth0();
  const [appointment, setAppointment] = useState<AppointmentModel | null>(null);
  const [newStatus, setNewStatus] = useState("");
  const [customer, setCustomer] = useState<any>(null); // adjust type as needed

  // Fetch the appointment details
  useEffect(() => {
    const fetchAppointment = async () => {
      try {
        const response = await axiosInstance.get<AppointmentModel>(
            `appointments/${appointmentId}`
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

  // Once the appointment is loaded, fetch the customer's details using appointment.customerId
  useEffect(() => {
    const fetchCustomer = async () => {
      try {
        if (!appointment?.customerId) return;
        const token = await getAccessTokenSilently();
        const response = await axiosInstance.get(
            `customers/${appointment.customerId}`,
            { headers: { Authorization: `Bearer ${token}` } }
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

  if (!appointment) {
    return (
        <div className="loading">Loading or appointment not found...</div>
    );
  }

  // Update status handler
  const handleStatusChange = async () => {
    try {
      if (!newStatus) {
        alert("Please select a status to update.");
        return;
      }
      const updated = await updateAppointmentStatus(
          appointment.appointmentId,
          newStatus
      );
      setAppointment(updated);
      alert("Status updated successfully!");
      setNewStatus("");
    } catch (err) {
      console.error("Error updating status:", err);
      alert("Failed to update status. Please try again.");
    }
  };

  return (
      <div className="appointment-details-container">
        <NavBar />
        <div className="appointment-details">
          <h2 className="title">Appointment Details</h2>
          {/* Flex container to hold the left (appointment details) and right (customer address) */}
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
                <span className="detail-value">
                {appointment.customerName}
              </span>
              </div>
            </div>

            <div className="address-details">
              <h3>Customer Address</h3>
              {customer ? (
                  <>
                    <div className="detail-item">
                      <span className="detail-label">Street:</span>
                      <span className="detail-value">
                    {customer.streetAddress}
                  </span>
                    </div>
                    <div className="detail-item">
                      <span className="detail-label">City:</span>
                      <span className="detail-value">{customer.city}</span>
                    </div>
                    <div className="detail-item">
                      <span className="detail-label">Postal Code:</span>
                      <span className="detail-value">
                    {customer.postalCode}
                  </span>
                    </div>
                    <div className="detail-item">
                      <span className="detail-label">Province:</span>
                      <span className="detail-value">
                    {customer.province}
                  </span>
                    </div>
                    <div className="detail-item">
                      <span className="detail-label">Country:</span>
                      <span className="detail-value">
                    {customer.country}
                  </span>
                    </div>
                  </>
              ) : (
                  <p>Loading customer address...</p>
              )}
            </div>
          </div>

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
        </div>
      </div>
  );
}