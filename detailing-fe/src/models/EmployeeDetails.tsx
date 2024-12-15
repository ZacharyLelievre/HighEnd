import './EmployeeDetails.css'; // Reusing the same CSS
import { useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";
import { EmployeeModel } from "./dtos/EmployeeModel"; // Importing EmployeeModel
import axios from "axios";
import {NavBar} from "../nav/NavBar";

export default function EmployeeDetails(): JSX.Element {
    const { employeeId } = useParams<{ employeeId: string }>();
    const [employee, setEmployee] = useState<EmployeeModel | null>(null);

    useEffect(() => {
        const fetchEmployeeDetails = async (): Promise<void> => {
            try {
                const response = await axios.get(`http://localhost:8080/api/employees/${employeeId}`);
                setEmployee(response.data);
            } catch (error) {
                console.error("Error fetching employee details: ", error);
            }
        };
        fetchEmployeeDetails();
    }, [employeeId]);

    if (!employee) {
        return <div>Loading...</div>;
    }

    return (
        <><NavBar/>
            <div className="details-container">
                {/* Profile Header */}
                <div className="profile-header">
                    <img
                        className="employee-image"
                        src={`http://localhost:8080/${employee.imagePath}`}
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
                        <input type="text" value={employee.first_name || "N/A"} readOnly/>
                    </div>
                    <div className="field">
                        <label>Last Name</label>
                        <input type="text" value={employee.last_name || "N/A"} readOnly/>
                    </div>
                    <div className="field">
                        <label>Position</label>
                        <input type="text" value={employee.position || "N/A"} readOnly/>
                    </div>
                    <div className="field">
                        <label>Email</label>
                        <input type="text" value={employee.email || "N/A"} readOnly/>
                    </div>
                    <div className="field">
                        <label>Phone</label>
                        <input type="text" value={employee.phone || "N/A"} readOnly/>
                    </div>
                    <div className="field">
                        <label>Salary</label>
                        <input type="text" value={`$${employee.salary.toFixed(2)}` || "N/A"} readOnly/>
                    </div>
                </div>

                {/* Email Section */}
                <div className="email-section">
                    <h3>My Email Address</h3>
                    <div className="email">
                        <p>{employee.email}</p>
                        <span>1 month ago</span>
                    </div>
                    <button>+ Add Email Address</button>
                </div>
            </div>
        </>
    );
}
