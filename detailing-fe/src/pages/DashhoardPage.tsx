import React, { useState } from "react";
import axios from "axios";
import { NavBar } from "../nav/NavBar";
import AllCustomers from "../models/AllCustomers";
import AllAppointments from "../models/AllAppointments";
import AllEmployees from "../models/allEmployees";
import "./DashboardPage.css";

export default function DashboardPage(): JSX.Element {
  const [inviteLink, setInviteLink] = useState("");
  const [isOpen, setIsOpen] = useState(false); // Toggle state for the container

  const handleGenerateLink = async () => {
    try {
      const response = await axios.post(
        "https://highend-zke6.onrender.com/api/employee-invites",
        {},
      );
      setInviteLink(response.data.inviteLink);
      setIsOpen(true); // Open the container
    } catch (error) {
      console.error("Error generating invite link:", error);
    }
  };

  const handleClose = () => {
    setIsOpen(false); // Close the container
    setInviteLink(""); // Optional: Clear the invite link
  };

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

        <div className="invite-section-container">
          {!isOpen && (
            <button className="generate-button" onClick={handleGenerateLink}>
              Generate Employee Invite
            </button>
          )}

          {isOpen && (
            <div className="invite-link-container">
              <button className="close-button" onClick={handleClose}>
                &times;
              </button>
              <p className="invite-link-text">
                Share this link with your new employee:
              </p>
              <textarea value={inviteLink} readOnly />
            </div>
          )}
        </div>
        <div className="section-container">
          <h2 className="section-title">Employees</h2>
          <AllEmployees />
        </div>
      </div>
    </div>
  );
}
