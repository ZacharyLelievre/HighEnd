import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosInstance from "../apis/axiosInstance";
import { AppointmentModel } from "../models/dtos/AppointmentModel";

export function AppointmentDetailPage() {
    const { appointmentId } = useParams();
    const [appointment, setAppointment] = useState<AppointmentModel | null>(null);
    const [status, setStatus] = useState<string>("");

    useEffect(() => {
        const fetchAppointment = async () => {
            try {
                const response = await axiosInstance.get(`appointments/${appointmentId}`);
                const appt: AppointmentModel = response.data;
                setAppointment(appt);
                setStatus(appt.status);
            } catch (err) {
                console.error("Error fetching appointment", err);
            }
        };
        fetchAppointment();
    }, [appointmentId]);

    // For updating status with your existing endpoint:  PUT /appointments/{id}/status
    const handleStatusChange = async (newStatus: string) => {
        if (!appointmentId) return;
        try {
            await axiosInstance.put(`appointments/${appointmentId}/status`, { status: newStatus });
            setStatus(newStatus);  // update local UI
        } catch (err) {
            console.error("Error updating status", err);
        }
    };

    if (!appointment) {
        return <div>Loading appointment...</div>;
    }

    return (
        <div style={{ padding: "20px" }}>
            <h2>Appointment Details</h2>
            <p><strong>Service Name:</strong> {appointment.serviceName}</p>
            <p><strong>Date:</strong> {appointment.appointmentDate}</p>
            <p><strong>Time:</strong> {appointment.appointmentTime}</p>
            <p><strong>Status:</strong> {status}</p>

            {/* Dropdown for status */}
            <select value={status} onChange={(e) => handleStatusChange(e.target.value)}>
                <option value="PENDING">Pending</option>
                <option value="CANCELLED">Cancelled</option>
                <option value="IN_PROGRESS">In Progress</option>
                <option value="COMPLETED">Completed</option>
            </select>
        </div>
    );
}