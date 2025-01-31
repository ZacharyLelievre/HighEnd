// src/pages/MyAppointmentDetails.tsx
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosInstance from "../apis/axiosInstance";
import { AppointmentModel } from "../models/dtos/AppointmentModel";
import { updateAppointmentStatus } from "../apis/updateAppointmentStatus";
import { NavBar } from "../nav/NavBar";
import "./MyAppointmentDetails.css"; // Ensure this import remains for styling

export function MyAppointmentDetails() {
  const { appointmentId } = useParams();
  const [appointment, setAppointment] = useState<AppointmentModel | null>(null);
  const [newStatus, setNewStatus] = useState("");

  // Fetch a single appointment by ID
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

  if (!appointment) {
    return <div className="loading">Loading or appointment not found...</div>;
  }

  // Update status
  const handleStatusChange = async () => {
    try {
      if (!newStatus) {
        alert("Please select a status to update.");
        return;
      }
      const updated = await updateAppointmentStatus(
        appointment.appointmentId,
        newStatus,
      );
      setAppointment(updated);
      alert("Status updated successfully!");
      setNewStatus(""); // Reset the dropdown after successful update
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
          {/* Add more detail items as needed */}
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
            disabled={!newStatus} // Disable button if no status is selected
          >
            Save
          </button>
        </div>
      </div>
    </div>
  );
}
