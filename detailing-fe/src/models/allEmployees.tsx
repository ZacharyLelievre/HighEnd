// src/models/AllEmployees.tsx

import React, { useEffect, useState } from "react";
import { EmployeeModel } from "./dtos/EmployeeModel";
import "./AllEmployees.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import AssignRoleDropdown from "./AssignRoleDropdown"; // Import the component

export default function AllEmployees(): JSX.Element {
  const [employees, setEmployees] = useState<EmployeeModel[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchEmployees = async (): Promise<void> => {
      try {
        const response = await axios.get(
            "https://highend-zke6.onrender.com/api/employees",
        );
        setEmployees(response.data);
      } catch (error) {
        console.error("Error fetching employees:", error);
      }
    };

    fetchEmployees();
  }, []);

  const handleViewEmployee = (employeeId: string): void => {
    navigate(`/employees/${employeeId}`); // Navigate to EmployeeDetails
  };

  return (
      <div className="employees-container">
        {employees.map((employee) => (
            <div className="employee-box" key={employee.employeeId}>
              <img
                  className="employee-image"
                  src={`https://highend-zke6.onrender.com/${employee.imagePath}`}
                  alt={`${employee.first_name} ${employee.last_name}`}
              />
              <div className="employee-details">
                <p>
                  <strong>Name:</strong> {employee.first_name} {employee.last_name}
                </p>
                <p>
                  <strong>Position:</strong> {employee.position}
                </p>
                <p>
                  <strong>Email:</strong> {employee.email}
                </p>
                <p>
                  <strong>Phone:</strong> {employee.phone}
                </p>
                <button onClick={() => handleViewEmployee(employee.employeeId)}>
                  View
                </button>
                {/* Include AssignRoleDropdown and pass the required userId prop */}
                <AssignRoleDropdown userId={employee.employeeId} />
              </div>
            </div>
        ))}
      </div>
  );
}