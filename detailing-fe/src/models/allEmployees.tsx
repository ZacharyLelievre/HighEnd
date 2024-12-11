import React, { useEffect, useState } from "react";
import { EmployeeModel } from "./dtos/EmployeeModel";
import "./AllEmployees.css";
import axios from "axios";

export default function AllEmployees(): JSX.Element {
    const [employees, setEmployees] = useState<EmployeeModel[]>([]);

    useEffect(() => {
        const fetchEmployees = async (): Promise<void> => {
            try {
                const response = await axios.get("http://localhost:8080/api/employees");
                setEmployees(response.data);
            } catch (error) {
                console.error("Error fetching employees:", error);
            }
        };

        fetchEmployees();
    }, []);

    return (
        <div>
            <h2 style={{ textAlign: "center" }}>Employees</h2>
            <div className="employees-container">
                {employees.map(employee => (
                    <div className="employee-box" key={employee.employeeId}>
                        <img
                            className="employee-image"
                            src={`http://localhost:8080/${employee.imagePath}`}
                            alt="employee"
                        />
                        <div className="employee-details">
                            <p><strong>Name:</strong> {employee.firstName }</p>
                            <p><strong>Position:</strong> {employee.position}</p>
                            <p><strong>Position:</strong> {employee.position}</p>
                            <p><strong>Email:</strong> {employee.email}</p>
                            <p><strong>Phone:</strong> {employee.phone}</p>
                            <button>View</button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}