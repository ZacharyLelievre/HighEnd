// src/pages/DashboardPage.tsx

import React, { useEffect, useState } from "react";
import { NavBar } from "../nav/NavBar";
import AllCustomers from "../models/AllCustomers";
import AllAppointments from "../models/AllAppointments";
import AddEmployee from "../models/AddEmployee";
import "./DashboardPage.css";
import axios from "axios";
import { useAuth0 } from "@auth0/auth0-react";
import AllEmployees from "../models/allEmployees";

interface EmployeeInfo {
  employeeId: string;
  first_name: string;
  last_name: string;
  position: string;
  email: string;
  phone: string;
  salary: number;
  imagePath: string;
}

export default function DashboardPage(): JSX.Element {
  const { getAccessTokenSilently, user, isAuthenticated } = useAuth0();
  const [isAddEmployeeOpen, setIsAddEmployeeOpen] = useState(false);

  // This is optional if you want to do the "createOrLinkEmployee" in Dashboard
  // But we'll replicate it in Home (callback) instead, as that’s your sign-up landing
  useEffect(() => {
    // If you want to do any employee fetch, do it here
  }, [isAuthenticated]);

  const openAddEmployee = () => setIsAddEmployeeOpen(true);
  const closeAddEmployee = () => setIsAddEmployeeOpen(false);

  return (
      <div>
        <NavBar />
        <div className="dashboard-container">
          <div className="section-container">
            <h2 className="section-title">Appointments</h2>
            <AllAppointments />
          </div>

          <div className="section-container">
            <h2 className="section-title">Customers</h2>
            <AllCustomers />
          </div>

          <div className="section-container">
            <h2 className="section-title">Employees</h2>
            <button className="add-employee-button" onClick={openAddEmployee}>
              Add Employee
            </button>
            <AllEmployees />
          </div>
        </div>

        {isAddEmployeeOpen && <AddEmployee onClose={closeAddEmployee} />}
      </div>
  );
}