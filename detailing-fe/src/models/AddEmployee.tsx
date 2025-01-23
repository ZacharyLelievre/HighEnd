// src/models/AddEmployee.tsx

import React, { useState } from "react";
import "./AddEmployee.css";
import { useAuth0 } from "@auth0/auth0-react";

interface AddEmployeeProps {
    onClose: () => void;
}

const AddEmployee: React.FC<AddEmployeeProps> = ({ onClose }) => {
    // Local state for employee form inputs
    const [formData, setFormData] = useState({
        first_name: "",
        last_name: "",
        position: "",
        email: "",
        phone: "",
        salary: "",
        imagePath: "",
    });

    // Auth0 hook
    const { loginWithRedirect, isAuthenticated, logout } = useAuth0();

    // Generic handler for text inputs
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    // 1. Save the form data to localStorage
    // 2. Redirect to Auth0 for sign-up
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        console.log("Employee Form Data to be saved:", formData);
        localStorage.setItem("employeeFormData", JSON.stringify(formData));

        // If you're already logged in, optionally log out so you can create a new user
        if (isAuthenticated) {
            await logout({ logoutParams: { returnTo: window.location.origin } });
        }
        // Redirect the user to Auth0 sign-up
        await loginWithRedirect({
            // If you want to explicitly show sign-up
            authorizationParams: {
                screen_hint: "signup",
            },
        });
    };

    return (
        <form onSubmit={handleSubmit} className="onboarding-employee-form">
            <h2>Employee Onboarding</h2>

            <input
                type="text"
                name="first_name"
                placeholder="First Name"
                onChange={handleChange}
                required
            />
            <input
                type="text"
                name="last_name"
                placeholder="Last Name"
                onChange={handleChange}
                required
            />
            <input
                type="text"
                name="position"
                placeholder="Position"
                onChange={handleChange}
                required
            />
            <input
                type="email"
                name="email"
                placeholder="Email"
                onChange={handleChange}
                required
            />
            <input
                type="tel"
                name="phone"
                placeholder="Phone Number"
                onChange={handleChange}
                required
            />
            <input
                type="number"
                name="salary"
                placeholder="Salary"
                onChange={handleChange}
                required
            />
            <input
                type="text"
                name="imagePath"
                placeholder="Image Path (e.g. profile.png)"
                onChange={handleChange}
            />

            <button type="submit">Submit</button>
        </form>
    );
};

export default AddEmployee;