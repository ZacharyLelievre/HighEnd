import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./CustomerDetails.css";

import { CustomerModel } from "./dtos/CustomerModel";

export default function CustomerDetails(): JSX.Element {
    const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;
    const { customerId } = useParams<{ customerId: string }>();
    const [customer, setCustomer] = useState<CustomerModel | null>(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchCustomer = async (): Promise<void> => {
            try {
                const response = await axios.get(`${apiBaseUrl}/customers/${customerId}`);
                setCustomer(response.data);
            } catch (error) {
                console.error("Error fetching customer details:", error);
            } finally {
                setIsLoading(false);
            }
        };

        if (customerId) {
            fetchCustomer();
        }
    }, [customerId, apiBaseUrl]);

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (!customer) {
        return <div>No customer found.</div>;
    }

    return (
        <div className="page-wrapper">
            <div className="customer-details-container">
                <h2>
                    {customer.customerFirstName} {customer.customerLastName}
                </h2>
                <p>
                    <strong>Customer ID:</strong> {customer.customerId}
                </p>
                <p>
                    <strong>Email:</strong> {customer.customerEmailAddress}
                </p>
                <p>
                    <strong>Street Address:</strong> {customer.streetAddress}
                </p>
                <p>
                    <strong>City:</strong> {customer.city}
                </p>
                <p>
                    <strong>Province:</strong> {customer.province}
                </p>
                <p>
                    <strong>Country:</strong> {customer.country}
                </p>
                <p>
                    <strong>Postal Code:</strong> {customer.postalCode}
                </p>
            </div>
        </div>
    );
}