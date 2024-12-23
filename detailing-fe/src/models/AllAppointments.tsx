import React, { useEffect, useState } from "react";
import axios from "axios";
import { AppointmentModel } from "./dtos/AppointmentModel";
import { EmployeeModel } from "./dtos/EmployeeModel";
import "./AllAppointments.css";
import { useAuth0 } from "@auth0/auth0-react";

export default function AllAppointments(): JSX.Element {
    const [appointments, setAppointments] = useState<AppointmentModel[]>([]);
    const [employees, setEmployees] = useState<EmployeeModel[]>([]);
    const [selectedEmployee, setSelectedEmployee] = useState<{ [key: string]: string }>({});
    const [loadingAppointments, setLoadingAppointments] = useState<boolean>(true);
    const [loadingEmployees, setLoadingEmployees] = useState<boolean>(true);

    const { getAccessTokenSilently } = useAuth0();

    useEffect(() => {
        const fetchAppointments = async (): Promise<void> => {
            try {
                const token = await getAccessTokenSilently();
                const response = await axios.get("http://localhost:8080/api/appointments", {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setAppointments(response.data);
            } catch (error) {
                console.error("Error fetching appointments:", error);
            } finally {
                setLoadingAppointments(false);
            }
        };

        fetchAppointments();
    }, [getAccessTokenSilently]);

    useEffect(() => {
        const fetchEmployees = async (): Promise<void> => {
            try {
                const token = await getAccessTokenSilently();
                const response = await axios.get("http://localhost:8080/api/employees", {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setEmployees(response.data);
            } catch (error) {
                console.error("Error fetching employees:", error);
            } finally {
                setLoadingEmployees(false);
            }
        };

        fetchEmployees();
    }, [getAccessTokenSilently]);

    // Handle Confirm Appointment
    const handleConfirm = async (appointmentId: string): Promise<void> => {
        try {
            const token = await getAccessTokenSilently();
            const response = await axios.put(
                `http://localhost:8080/api/appointments/${appointmentId}/status`,
                { status: "CONFIRMED" },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
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
            alert("Error confirming appointment. Please try again.");
        }
    };

    const handleAssignEmployee = async (appointmentId: string): Promise<void> => {
        const employeeId = selectedEmployee[appointmentId];

        if (!employeeId) {
            alert("Please select an employee.");
            return;
        }

        try {
            const token = await getAccessTokenSilently();
            const response = await axios.put(
                `http://localhost:8080/api/appointments/${appointmentId}/assign`,
                { employeeId: employeeId },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            setAppointments(prevAppointments =>
                prevAppointments.map(appointment =>
                    appointment.appointmentId === appointmentId
                        ? { ...appointment, employeeName: response.data?.employeeName || "N/A" }
                        : appointment
                )
            );

            alert(`Employee assigned successfully to appointment ID: ${appointmentId}`);
        } catch (error) {
            console.error("Error assigning employee:", error);
            alert("Error assigning employee. Please try again.");
        }
    };

    const handleEmployeeChange = (appointmentId: string, employeeId: string): void => {
        setSelectedEmployee(prevSelectedEmployee => ({
            ...prevSelectedEmployee,
            [appointmentId]: employeeId
        }));
    };

    return (
        <div>
            {loadingAppointments ? (
                <p>Loading appointments...</p>
            ) : (
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
                                <p><strong>Employee Name:</strong> {appointment.employeeName || "Not Assigned"}</p>
                                <p><strong>Status:</strong> {appointment.status}</p>

                                <label htmlFor={`employee-select-${appointment.appointmentId}`}><strong>Assign Employee:</strong></label>
                                {loadingEmployees ? (
                                    <p>Loading employees...</p>
                                ) : (
                                    <select
                                        id={`employee-select-${appointment.appointmentId}`}
                                        value={selectedEmployee[appointment.appointmentId] || ""}
                                        onChange={(e) => handleEmployeeChange(appointment.appointmentId, e.target.value)}
                                    >
                                        <option value="">Select Employee</option>
                                        {employees.map(employee => (
                                            <option key={employee.employeeId} value={employee.employeeId}>
                                                {employee.first_name} {employee.last_name}
                                            </option>
                                        ))}
                                    </select>
                                )}

                                <div className="button-container">
                                    <button onClick={() => handleAssignEmployee(appointment.appointmentId)}>
                                        Assign
                                    </button>
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
            )}
        </div>
    );
}