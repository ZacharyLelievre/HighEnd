import React, { useEffect, useState } from "react";
import axios from "axios";
import { AppointmentModel } from "./dtos/AppointmentModel";
import { EmployeeModel } from "./dtos/EmployeeModel";
import "./AllAppointments.css";
import { useAuth0 } from "@auth0/auth0-react";

interface AvailableEmpMap {
  [appointmentId: string]: EmployeeModel[]; // For each appt, store an array of employees
}

export default function AllAppointments(): JSX.Element {
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;
  const { getAccessTokenSilently } = useAuth0();

  // All appointments
  const [appointments, setAppointments] = useState<AppointmentModel[]>([]);
  const [loadingAppointments, setLoadingAppointments] = useState<boolean>(true);

  // For each appointment: is it loading employees right now?
  const [loadingEmployeesMap, setLoadingEmployeesMap] = useState<{
    [id: string]: boolean;
  }>({});

  // For each appointment, we store an array of employees who are actually available
  const [availableEmpsMap, setAvailableEmpsMap] = useState<AvailableEmpMap>({});

  // Which employee is selected in the dropdown for each appointment
  const [selectedEmployee, setSelectedEmployee] = useState<{
    [id: string]: string;
  }>({});

  const [confirmationMessage, setConfirmationMessage] = useState("");

  // Rescheduling states
  const [showRescheduleModal, setShowRescheduleModal] = useState(false);
  const [selectedAppointment, setSelectedAppointment] =
    useState<AppointmentModel | null>(null);
  const [newDate, setNewDate] = useState("");
  const [newStartTime, setNewStartTime] = useState("");
  const [newEndTime, setNewEndTime] = useState("");

  // 1) Fetch all appointments once
  useEffect(() => {
    const fetchAppointments = async (): Promise<void> => {
      try {
        const token = await getAccessTokenSilently();
        const response = await axios.get(`${apiBaseUrl}/appointments`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setAppointments(response.data);
      } catch (error) {
        console.error("Error fetching appointments:", error);
      } finally {
        setLoadingAppointments(false);
      }
    };
    fetchAppointments();
  }, [getAccessTokenSilently, apiBaseUrl]);

  // 2) For each appointment, fetch only the employees who are available for that date & time
  const fetchAvailableEmployees = async (appt: AppointmentModel) => {
    if (
      !appt.appointmentDate ||
      !appt.appointmentTime ||
      !appt.appointmentEndTime
    )
      return;
    try {
      // mark employees as "loading" for this specific appointment
      setLoadingEmployeesMap((prev) => ({
        ...prev,
        [appt.appointmentId]: true,
      }));

      const token = await getAccessTokenSilently();
      const params = new URLSearchParams({
        date: appt.appointmentDate,
        startTime: appt.appointmentTime,
        endTime: appt.appointmentEndTime,
      });
      const resp = await axios.get<EmployeeModel[]>(
        `${apiBaseUrl}/employees/available?${params.toString()}`,
        { headers: { Authorization: `Bearer ${token}` } },
      );

      setAvailableEmpsMap((prev) => ({
        ...prev,
        [appt.appointmentId]: resp.data,
      }));
    } catch (err) {
      console.error("Error fetching available employees:", err);
    } finally {
      setLoadingEmployeesMap((prev) => ({
        ...prev,
        [appt.appointmentId]: false,
      }));
    }
  };

  // 3) Whenever we get or update the appointments list, fetch employees for each appointment
  useEffect(() => {
    appointments.forEach((appt) => {
      fetchAvailableEmployees(appt);
    });
  }, [appointments]);

  // 4) Deleting an appointment (renamed delete button references)
  const handleDelete = async (appointmentId: string): Promise<void> => {
    if (!window.confirm("Are you sure you want to delete this appointment?"))
      return;
    try {
      const token = await getAccessTokenSilently();
      await axios.delete(`${apiBaseUrl}/appointments/${appointmentId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAppointments((prev) =>
        prev.filter((a) => a.appointmentId !== appointmentId),
      );
      alert("Appointment deleted successfully.");
    } catch (error) {
      console.error("Error deleting appointment:", error);
      alert("Error deleting appointment. Please try again.");
    }
  };

  // 5) Confirm appointment
  const handleConfirm = async (appointmentId: string): Promise<void> => {
    try {
      const token = await getAccessTokenSilently();
      const response = await axios.put(
        `${apiBaseUrl}/appointments/${appointmentId}/status`,
        { status: "CONFIRMED" },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        },
      );
      console.log("Appointment confirmed:", response.data);

      setAppointments((prev) =>
        prev.map((appt) =>
          appt.appointmentId === appointmentId
            ? { ...appt, status: "CONFIRMED" }
            : appt,
        ),
      );
      const customerEmail = response.data.customerEmailAddress;
      setConfirmationMessage(
        `Confirmation email sent successfully, to ${customerEmail}`,
      );
      setTimeout(() => {
        setConfirmationMessage("");
      }, 3000);
    } catch (error) {
      console.error("Error confirming appointment:", error);
      alert("Error confirming appointment. Please try again.");
    }
  };

  // 6) Assign an employee from the dropdown
  const handleAssignEmployee = async (appointmentId: string): Promise<void> => {
    const employeeId = selectedEmployee[appointmentId];
    if (!employeeId) {
      alert("Please select an employee.");
      return;
    }

    try {
      const token = await getAccessTokenSilently();
      const response = await axios.put(
        `${apiBaseUrl}/appointments/${appointmentId}/assign`,
        { employeeId },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        },
      );
      setAppointments((prev) =>
        prev.map((appt) =>
          appt.appointmentId === appointmentId
            ? { ...appt, employeeName: response.data?.employeeName || "N/A" }
            : appt,
        ),
      );
      alert(
        `Employee assigned successfully to appointment ID: ${appointmentId}`,
      );
    } catch (error) {
      console.error("Error assigning employee:", error);
      alert("Error assigning employee. Please try again.");
    }
  };

  // 7) Track which employee is chosen
  const handleEmployeeChange = (
    appointmentId: string,
    employeeId: string,
  ): void => {
    setSelectedEmployee((prev) => ({
      ...prev,
      [appointmentId]: employeeId,
    }));
  };

  // 8) Rescheduling logic
  const handleRescheduleClick = (appointment: AppointmentModel): void => {
    setSelectedAppointment(appointment);
    setNewDate(appointment.appointmentDate);
    setNewStartTime(appointment.appointmentTime);
    setNewEndTime(appointment.appointmentEndTime || "");
    setShowRescheduleModal(true);
  };

  const handleReschedule = async (): Promise<void> => {
    if (!selectedAppointment) return;
    try {
      const token = await getAccessTokenSilently();
      await axios.put(
        `${apiBaseUrl}/appointments/${selectedAppointment.appointmentId}/reschedule`,
        { newDate, newStartTime, newEndTime },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        },
      );
      setAppointments((prev) =>
        prev.map((appt) =>
          appt.appointmentId === selectedAppointment.appointmentId
            ? {
                ...appt,
                appointmentDate: newDate,
                appointmentTime: newStartTime,
                appointmentEndTime: newEndTime,
              }
            : appt,
        ),
      );
      alert("Appointment rescheduled successfully.");
      setShowRescheduleModal(false);
    } catch (error) {
      console.error("Error rescheduling appointment:", error);
      alert("Error rescheduling appointment. Please try again.");
    }
  };

  return (
    <div>
      {confirmationMessage && (
        <div
          style={{
            backgroundColor: "lightgreen",
            padding: "10px",
            borderRadius: "4px",
            marginBottom: "10px",
            textAlign: "center",
            fontWeight: "bold",
          }}
        >
          {confirmationMessage}
        </div>
      )}
      {loadingAppointments ? (
        <p>Loading appointments...</p>
      ) : (
        <div className="appointments-container">
          {appointments.map((appointment) => {
            const { appointmentId } = appointment;
            const loadingEmps = loadingEmployeesMap[appointmentId];
            const possibleEmployees = availableEmpsMap[appointmentId] || [];

            return (
              <div className="appointment-box" key={appointmentId}>
                <div className="appointment-details">
                  <p>
                    <strong>Date:</strong> {appointment.appointmentDate}
                  </p>
                  <p>
                    <strong>Time:</strong> {appointment.appointmentTime} -{" "}
                    {appointment.appointmentEndTime}
                  </p>
                  <p>
                    <strong>Service Name:</strong> {appointment.serviceName}
                  </p>
                  <p>
                    <strong>Customer Name:</strong> {appointment.customerName}
                  </p>
                  <p>
                    <strong>Employee Name:</strong>{" "}
                    {appointment.employeeName || "Not Assigned"}
                  </p>
                  <p>
                    <strong>Status:</strong> {appointment.status}
                  </p>

                  <label>
                    <strong>Assign Employee:</strong>
                  </label>
                  {loadingEmps ? (
                    <p>Loading available employees...</p>
                  ) : (
                    <select
                      value={selectedEmployee[appointmentId] || ""}
                      onChange={(e) =>
                        handleEmployeeChange(appointmentId, e.target.value)
                      }
                    >
                      <option value="">Select Employee</option>
                      {possibleEmployees.map((emp) => (
                        <option key={emp.employeeId} value={emp.employeeId}>
                          {emp.first_name} {emp.last_name}
                        </option>
                      ))}
                    </select>
                  )}

                  <div className="button-container">
                    <button onClick={() => handleAssignEmployee(appointmentId)}>
                      Assign
                    </button>
                    <button
                      onClick={() => handleConfirm(appointmentId)}
                      disabled={appointment.status === "CONFIRMED"}
                    >
                      {appointment.status === "CONFIRMED"
                        ? "Confirmed"
                        : "Confirm"}
                    </button>
                    <button
                      className="remove-appointment-button"
                      onClick={() => handleDelete(appointmentId)}
                    >
                      Delete
                    </button>
                    <button onClick={() => handleRescheduleClick(appointment)}>
                      Reschedule
                    </button>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}

      {showRescheduleModal && (
        <div className="modal">
          <h3>Reschedule Appointment</h3>
          <label>Date:</label>
          <input
            type="date"
            value={newDate}
            onChange={(e) => setNewDate(e.target.value)}
          />
          <label>Start Time:</label>
          <input
            type="time"
            value={newStartTime}
            onChange={(e) => setNewStartTime(e.target.value)}
          />
          <label>End Time:</label>
          <input
            type="time"
            value={newEndTime}
            onChange={(e) => setNewEndTime(e.target.value)}
          />
          <div className="modal-buttons">
            <button onClick={handleReschedule}>Confirm</button>
            <button onClick={() => setShowRescheduleModal(false)}>
              Cancel
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
