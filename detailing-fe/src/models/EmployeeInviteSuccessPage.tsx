import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth0 } from "@auth0/auth0-react";
import "./EmployeeInviteSuccessPage.css";

export function EmployeeInviteSuccessPage() {
  const { isAuthenticated, getIdTokenClaims } = useAuth0();
  const [status, setStatus] = useState("Loading...");

  useEffect(() => {
    async function doStuff() {
      if (!isAuthenticated) {
        setStatus("Could not find ID token; are you sure you just signed up?");
        return;
      }
      const claims = await getIdTokenClaims();
      if (!claims) {
        setStatus("No claims found; are you sure you just signed up?");
        return;
      }
      const sub = claims.sub;

      const formJson = localStorage.getItem("employeeFormData");
      if (!formJson) {
        setStatus("No employee form data found.");
        return;
      }
      const data = JSON.parse(formJson);

      try {
        await axios.post("https://highend-zke6.onrender.com/api/employees", {
          employeeId: sub,
          first_name: data.firstName,
          last_name: data.lastName,
          position: data.position,
          email: data.email,
          phone: data.phone,
        });
        setStatus("✅ Success! Your employee account is created.");
        localStorage.removeItem("employeeFormData");
      } catch (err) {
        console.error("Error creating employee:", err);
        setStatus("❌ An error occurred while creating your employee account.");
      }
    }
    doStuff();
  }, [isAuthenticated, getIdTokenClaims]);

  return (
      <div className="invite-success-container">
        <div className="invite-success-message">{status}</div>
      </div>
  );
}