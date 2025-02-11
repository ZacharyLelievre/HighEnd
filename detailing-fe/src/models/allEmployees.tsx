import React, { useEffect, useState } from "react";
import { EmployeeModel } from "./dtos/EmployeeModel";
import "./AllEmployees.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function AllEmployees(): JSX.Element {
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;

  const [employees, setEmployees] = useState<EmployeeModel[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchEmployees = async (): Promise<void> => {
      try {
        const response = await axios.get(`${apiBaseUrl}/employees`);
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
    <div>
      <div className="employees-container">
        {employees.map((employee) => (
          <div className="employee-box" key={employee.employeeId}>
            <img
              className="employee-image"
              src={`https://highend-zke6.onrender.com/${employee.imagePath}`}
              alt="employee"
            />
            <div className="employee-details">
              <p>
                <strong>Name:</strong> {employee.first_name},{" "}
                {employee.last_name}
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
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
