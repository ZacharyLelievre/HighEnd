import { NavBar } from "../nav/NavBar";
import AllCustomers from "../models/AllCustomers";
import AllAppointments from "../models/AllAppointments";

import React from "react";
import "./DashboardPage.css";
import AllEmployees from "../models/allEmployees";

export default function DashboardPage(): JSX.Element {
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
          <AllEmployees />
        </div>
      </div>
    </div>
  );
}
