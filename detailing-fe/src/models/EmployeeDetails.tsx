import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { useAuth0 } from "@auth0/auth0-react";

import { NavBar } from "../nav/NavBar";
import { EmployeeModel } from "./dtos/EmployeeModel";
import { AvailabilityModel } from "./dtos/AvailabilityModel";

import "./EmployeeDetails.css";

/** A list of valid days to enforce correct spelling. */
const VALID_DAYS = [
  "Monday",
  "Tuesday",
  "Wednesday",
  "Thursday",
  "Friday",
  "Saturday",
  "Sunday",
];

/** We'll display hours from 8..22 (8 AM up to 10 PM). */
const HOURS = Array.from({ length: 14 }, (_, i) => i + 8);
// That generates [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21]
// => 8:00 AM up to 21:00 (9 PM) if you want 10 PM, make it length=15 => up to 22

/**
 * Convert "HH:mm" => a fractional hour.
 * e.g. "08:30" => 8.5
 */
function parseTime(timeString: string): number {
  const [hh, mm] = timeString.split(":").map(Number);
  return hh + mm / 60;
}

/**
 * Check if a given numeric hour (e.g. 8, 9) is within
 * the startTime..endTime range of an availability object.
 * We consider an hour "covered" if hour >= startTime AND hour < endTime.
 */
function coversHour(avail: AvailabilityModel, hour: number) {
  const start = parseTime(avail.startTime);
  const end = parseTime(avail.endTime);
  return hour >= start && hour < end;
}

export default function EmployeeDetails(): JSX.Element {
  const { employeeId } = useParams<{ employeeId: string }>();
  const { getAccessTokenSilently } = useAuth0();

  // Employee data
  const [employee, setEmployee] = useState<EmployeeModel | null>(null);

  // Availabilities
  const [availability, setAvailability] = useState<AvailabilityModel[]>([]);

  // Form data for new availability
  const [newAvailability, setNewAvailability] = useState<AvailabilityModel>({
    dayOfWeek: "",
    startTime: "",
    endTime: "",
  });

  // ---------------------------
  // 1) Fetch Employee Details
  // ---------------------------
  useEffect(() => {
    const fetchEmployeeDetails = async (): Promise<void> => {
      if (!employeeId) return;
      try {
        const token = await getAccessTokenSilently();
        const response = await axios.get(
          `https://highend-zke6.onrender.com/api/employees/${employeeId}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          },
        );
        setEmployee(response.data);
      } catch (error) {
        console.error("Error fetching employee details:", error);
      }
    };

    fetchEmployeeDetails();
  }, [employeeId, getAccessTokenSilently]);

  // ---------------------------
  // 2) Fetch Availability from Server
  // ---------------------------
  const fetchAvailability = async (): Promise<void> => {
    if (!employeeId) return;
    try {
      const token = await getAccessTokenSilently();
      const response = await axios.get(
        `https://highend-zke6.onrender.com/api/employees/${employeeId}/availability`,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      setAvailability(response.data);
    } catch (error) {
      console.error("Error fetching availability:", error);
    }
  };

  useEffect(() => {
    fetchAvailability();
  }, [employeeId, getAccessTokenSilently]);

  // ---------------------------
  // 3) Handle Local Input Changes
  // ---------------------------
  // Update form fields
  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    const { name, value } = e.target;
    setNewAvailability((prev) => ({ ...prev, [name]: value }));
  };

  // Add new availability (locally)
  const addAvailability = () => {
    const { dayOfWeek, startTime, endTime } = newAvailability;

    // Basic validation
    if (!dayOfWeek || !startTime || !endTime) {
      alert("Please fill out all fields.");
      return;
    }
    if (!VALID_DAYS.includes(dayOfWeek)) {
      alert(`Day of week must be a valid day. E.g. "Monday", "Tuesday"...`);
      return;
    }

    // Ensure times are within 08:00-22:00
    const start = parseTime(startTime);
    const end = parseTime(endTime);
    if (start < 8 || end > 22) {
      alert("Time must be between 08:00 and 22:00.");
      return;
    }

    if (end <= start) {
      alert("End time must be after start time.");
      return;
    }

    setAvailability((prev) => [...prev, newAvailability]);
    // reset
    setNewAvailability({ dayOfWeek: "", startTime: "", endTime: "" });
  };

  // ---------------------------
  // 4) Save Availability (PUT)
  // ---------------------------
  const saveAvailability = async () => {
    if (!employeeId) return;
    try {
      const token = await getAccessTokenSilently();
      await axios.put(
        `https://highend-zke6.onrender.com/api/employees/${employeeId}/availability`,
        availability,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        },
      );
      alert("Availability updated successfully!");
      // re-fetch to confirm
      await fetchAvailability();
    } catch (error) {
      console.error("Error saving availability:", error);
      alert("Failed to update availability.");
    }
  };

  // ---------------------------
  // 5) Render a Weekly Grid
  // ---------------------------
  // We'll make a table with:
  //   columns = Monday..Sunday
  //   rows = 8..21 (or 8..22 if you prefer)
  // Each cell => highlight if that hour is covered by *any* availability that day
  // that is: if hour >= start && hour < end
  function renderAvailabilityGrid() {
    return (
      <table className="availability-grid">
        <thead>
          <tr>
            <th>Hour</th>
            {VALID_DAYS.map((day) => (
              <th key={day}>{day}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {HOURS.map((hour) => {
            const hourStr = `${hour}:00`; // e.g. "8:00"
            return (
              <tr key={hour}>
                <td>{hourStr}</td>
                {VALID_DAYS.map((day) => {
                  // Check if any AvailabilityModel for this day covers this hour
                  const isCovered = availability.some(
                    (avail) =>
                      avail.dayOfWeek === day && coversHour(avail, hour),
                  );
                  return (
                    <td
                      key={`${day}-${hour}`}
                      className={isCovered ? "covered-cell" : ""}
                    >
                      {isCovered ? "Available" : ""}
                    </td>
                  );
                })}
              </tr>
            );
          })}
        </tbody>
      </table>
    );
  }

  // ---------------------------
  // If employee not loaded, show spinner
  // ---------------------------
  if (!employee) {
    return <div>Loading...</div>;
  }

  // ---------------------------
  // RENDER
  // ---------------------------
  return (
    <>
      <NavBar />

      <div className="details-container">
        {/* --- Profile Header --- */}
        <div className="profile-header">
          {employee.imagePath && (
            <img
              className="employee-image"
              src={`https://highend-zke6.onrender.com/${employee.imagePath}`}
              alt="Employee"
            />
          )}
          <div className="info">
            <h2>{`${employee.first_name} ${employee.last_name}`}</h2>
            <p>{employee.email}</p>
          </div>
        </div>

        {/* --- Employee Info Fields --- */}
        <div className="details">
          <div className="field">
            <label>First Name</label>
            <input type="text" value={employee.first_name} readOnly />
          </div>
          <div className="field">
            <label>Last Name</label>
            <input type="text" value={employee.last_name} readOnly />
          </div>
          <div className="field">
            <label>Position</label>
            <input type="text" value={employee.position} readOnly />
          </div>
          <div className="field">
            <label>Email</label>
            <input type="text" value={employee.email} readOnly />
          </div>
          <div className="field">
            <label>Phone</label>
            <input type="text" value={employee.phone} readOnly />
          </div>
          <div className="field">
            <label>Salary</label>
            <input
              type="text"
              value={`$${employee.salary.toFixed(2)}`}
              readOnly
            />
          </div>
        </div>

        {/* --- Availability Grid --- */}
        <div className="availability-section">
          <h3>Weekly Availability</h3>
          {renderAvailabilityGrid()}

          {/* --- Add NEW Availability Form --- */}
          <div className="add-availability-form">
            <h4>Add Availability</h4>

            <div className="field">
              <label>Day of Week:</label>
              <select
                name="dayOfWeek"
                value={newAvailability.dayOfWeek}
                onChange={handleInputChange}
              >
                <option value="">-- Select a Day --</option>
                {VALID_DAYS.map((day) => (
                  <option key={day} value={day}>
                    {day}
                  </option>
                ))}
              </select>
            </div>

            <div className="field">
              <label>Start Time (HH:mm):</label>
              <input
                type="time"
                name="startTime"
                value={newAvailability.startTime}
                onChange={handleInputChange}
                min="08:00"
                max="22:00"
              />
            </div>

            <div className="field">
              <label>End Time (HH:mm):</label>
              <input
                type="time"
                name="endTime"
                value={newAvailability.endTime}
                onChange={handleInputChange}
                min="08:00"
                max="22:00"
              />
            </div>

            <button onClick={addAvailability}>Add Availability</button>
          </div>

          {/* --- Button to SAVE (PUT) the entire availability array to server --- */}
          <div style={{ marginTop: "1rem" }}>
            <button onClick={saveAvailability}>Save Availability</button>
          </div>
        </div>
      </div>
    </>
  );
}
