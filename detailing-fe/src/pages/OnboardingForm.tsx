import React, { useState } from 'react';
import { useAuth0 } from "@auth0/auth0-react";
import './OnboardingForm.css';

export function OnboardingForm() {
    const [formData, setFormData] = useState({
        customerFirstName: '',
        customerLastName: '',
        customerEmailAddress: '',
        streetAddress: '',
        city: '',
        postalCode: '',
        province: '',
        country: ''
    });

    const { loginWithRedirect } = useAuth0();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        // Save form in to the localStorage
        console.log('Form Data to be saved:', formData);
        localStorage.setItem('customFormData', JSON.stringify(formData));

        // Redirect to Auth0 login
        await loginWithRedirect();
    };


    return (
        <form onSubmit={handleSubmit}>
            <h2>Onboarding Form</h2>
            <input type="text" name="customerFirstName" placeholder="First Name" onChange={handleChange} required />
            <input type="text" name="customerLastName" placeholder="Last Name" onChange={handleChange} required />
            <input type="email" name="customerEmailAddress" placeholder="Email" onChange={handleChange} required />
            <input type="text" name="streetAddress" placeholder="Street Address" onChange={handleChange} required />
            <input type="text" name="city" placeholder="City" onChange={handleChange} required />
            <input type="text" name="postalCode" placeholder="Postal Code" onChange={handleChange} required />
            <input type="text" name="province" placeholder="Province" onChange={handleChange} required />
            <input type="text" name="country" placeholder="Country" onChange={handleChange} required />
            <button type="submit">Submit</button>
        </form>
    );
}