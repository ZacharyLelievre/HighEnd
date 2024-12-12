import React, { useEffect, useState } from "react";
import { AppointmentModel } from "./dtos/AppointmentModel";
import "./AllAppointments.css";
import axios from "axios";

export default function AllAppointments(): JSX.Element {
    const [appointments, setAppointments] = useState<AppointmentModel[]>([]);

    useEffect(() => {
        const fetchAppointments = async (): Promise<void> => {
            try {
                const response = await axios.get("http://localhost:8080/api/appointments");
                setAppointments(response.data);
            } catch (error) {
                console.error("Error fetching appointments:", error);
            }
        };

        fetchAppointments();
    }, []);
    const handleConfirm = async (appointmentId: string): Promise<void> => {
        try {
            const response = await axios.put(`http://localhost:8080/api/appointments/${appointmentId}/status`, {
                status: "CONFIRMED"
            });
            console.log("Appointment confirmed:", response.data);

            setAppointments(prevAppointments =>
                prevAppointments.map(appointment =>
                    appointment.appointmentId === appointmentId
                        ? { ...appointment, status: "CONFIRMED" }
                        : appointment
                )
            );
        } catch (error) {
            console.error("Error confirming appointment:", error);
        }
    };

    return (
        <div>
            <div className="appointments-container">
                {appointments.map(appointment => (
                    <div className="appointment-box" key={appointment.appointmentId}>
                        <img
                            className="appointment-image"
                            src={`http://localhost:8080/${appointment.imagePath}`}
                            alt="appointment"
                        />
                        <div className="appointment-details">
                            <p><strong>Date:</strong> {appointment.appointmentDate}</p>
                            <p><strong>Time:</strong> {appointment.appointmentTime}</p>
                            <p><strong>Service Name:</strong> {appointment.serviceName}</p>
                            <p><strong>Customer Name:</strong> {appointment.customerName}</p>
                            <p><strong>Employee Name:</strong> {appointment.employeeName}</p>
                            <p><strong>Status:</strong> {appointment.status}</p>
                            <div className="button-container">
                                <button>View</button>
                                <button
                                    onClick={() => handleConfirm(appointment.appointmentId)}
                                    disabled={appointment.status === "CONFIRMED"}
                                >
                                    {appointment.status === "CONFIRMED" ? "Confirmed" : "Confirm"}
                                </button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}