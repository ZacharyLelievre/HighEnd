import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { useAuth0 } from "@auth0/auth0-react";

export function EmployeeOnboardingForm() {
  const { token } = useParams<{ token: string }>();
  const { loginWithRedirect } = useAuth0();

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [position, setPosition] = useState("");
  const [phone, setPhone] = useState("");
  const [email, setEmail] = useState("");

  const [isInviteValid, setIsInviteValid] = useState<boolean | null>(null);

  useEffect(() => {
    async function validateToken() {
      try {
        const response = await axios.get(
          `https://highend-zke6.onrender.com/api/employee-invites/${token}`,
        );
        setIsInviteValid(response.data === true);
      } catch (err) {
        console.error("Error validating token", err);
        setIsInviteValid(false);
      }
    }
    validateToken();
  }, [token]);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // 1) Save form data
    const employeeFormData = { firstName, lastName, position, phone, email };
    localStorage.setItem("employeeFormData", JSON.stringify(employeeFormData));

    // 2) Launch Auth0's sign-up.
    await loginWithRedirect({
      authorizationParams: {
        prompt: "login",
        screen_hint: "signup",
      },
      appState: { returnTo: "/employee-invite-success" },
    });
  };

  if (isInviteValid === false) {
    return <div>Sorry, this invite link is invalid or expired.</div>;
  }
  if (isInviteValid === null) {
    return <div>Validating invite link...</div>;
  }

  return (
    <div style={{ marginTop: "40px" }}>
      <h2>Employee Onboarding Form</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="First Name"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Last Name"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Position (e.g. Detailer)"
          value={position}
          onChange={(e) => setPosition(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Phone #"
          value={phone}
          onChange={(e) => setPhone(e.target.value)}
          required
        />
        <input
          type="email"
          placeholder="Work Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />

        <button type="submit">Submit</button>
      </form>
    </div>
  );
}
