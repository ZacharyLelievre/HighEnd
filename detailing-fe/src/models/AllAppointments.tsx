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

    return (
        <div>
            <h2 style={{ textAlign: "center" }}>Appointments</h2>
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
                            <p><strong>Service ID:</strong> {appointment.serviceId}</p>
                            <p><strong>Customer ID:</strong> {appointment.customerId}</p>
                            <p><strong>Employee ID:</strong> {appointment.employeeId}</p>
                            <p><strong>Status:</strong> {appointment.status}</p>
                            <button>View</button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}