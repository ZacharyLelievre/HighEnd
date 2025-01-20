import "./EmployeeDetails.css"; // Reusing the same CSS
import { useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";
import { EmployeeModel } from "./dtos/EmployeeModel"; // Importing EmployeeModel
import { AvailabilityModel } from "./dtos/AvailabilityModel"; // Importing AvailabilityModel
import axios from "axios";
import { NavBar } from "../nav/NavBar";
import { useAuth0 } from "@auth0/auth0-react";

export default function EmployeeDetails(): JSX.Element {
  const { employeeId } = useParams<{ employeeId: string }>();
  const [employee, setEmployee] = useState<EmployeeModel | null>(null);
  const [availability, setAvailability] = useState<AvailabilityModel[]>([]); // State for availability
  const { getAccessTokenSilently } = useAuth0();

  // Fetch employee details
  useEffect(() => {
    const fetchEmployeeDetails = async (): Promise<void> => {
      try {
        const token = await getAccessTokenSilently();
        const response = await axios.get(
            `https://highend-zke6.onrender.com/api/employees/${employeeId}`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            },
        );
        setEmployee(response.data);
      } catch (error) {
        console.error("Error fetching employee details: ", error);
      }
    };

    fetchEmployeeDetails();
  }, [employeeId, getAccessTokenSilently]);

  // Fetch employee availability
  useEffect(() => {
    const fetchAvailability = async (): Promise<void> => {
      try {
        const token = await getAccessTokenSilently();
        const response = await axios.get(
            `https://highend-zke6.onrender.com/api/employees/${employeeId}/availability`,
            {
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
              },
            },
        );
        setAvailability(response.data);
      } catch (error) {
        console.error("Error fetching employee availability: ", error);
      }
    };

    fetchAvailability();
  }, [employeeId, getAccessTokenSilently]);

  if (!employee) {
    return <div>Loading...</div>;
  }

  return (
      <>
        <NavBar />
        <div className="details-container">
          {/* Profile Header */}
          <div className="profile-header">
            <img
                className="employee-image"
                src={`https://highend-zke6.onrender.com/${employee.imagePath}`}
                alt="employee"
            />
            <div className="info">
              <h2>{`${employee.first_name} ${employee.last_name}`}</h2>
              <p>{employee.email}</p>
            </div>
          </div>

          {/* Details Section */}
          <div className="details">
            <div className="field">
              <label>First Name</label>
              <input type="text" value={employee.first_name || "N/A"} readOnly />
            </div>
            <div className="field">
              <label>Last Name</label>
              <input type="text" value={employee.last_name || "N/A"} readOnly />
            </div>
            <div className="field">
              <label>Position</label>
              <input type="text" value={employee.position || "N/A"} readOnly />
            </div>
            <div className="field">
              <label>Email</label>
              <input type="text" value={employee.email || "N/A"} readOnly />
            </div>
            <div className="field">
              <label>Phone</label>
              <input type="text" value={employee.phone || "N/A"} readOnly />
            </div>
            <div className="field">
              <label>Salary</label>
              <input
                  type="text"
                  value={`$${employee.salary.toFixed(2)}` || "N/A"}
                  readOnly
              />
            </div>
          </div>

          {/* Availability Section */}
          <div className="availability-section">
            <h3>Availability</h3>
            {availability.length > 0 ? (
                <table>
                  <thead>
                  <tr>
                    <th>Day of Week</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                  </tr>
                  </thead>
                  <tbody>
                  {availability.map((avail, index) => (
                      <tr key={index}>
                        <td>{avail.dayOfWeek}</td>
                        <td>{avail.startTime}</td>
                        <td>{avail.endTime}</td>
                      </tr>
                  ))}
                  </tbody>
                </table>
            ) : (
                <p>No availability data available.</p>
            )}
          </div>
        </div>
      </>
  );
}
