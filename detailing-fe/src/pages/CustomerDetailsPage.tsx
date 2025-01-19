import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { CustomerModel } from "../models/dtos/CustomerModel";
import CustomerDetails from "./CustomerDetails";

export default function CustomerDetailsPage(): JSX.Element {
    const { customerId } = useParams<{ customerId: string }>();
    const [customer, setCustomer] = useState<CustomerModel | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchCustomer = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/customers/${customerId}`);
                const data = await response.json();
                setCustomer(data);
            } catch (error) {
                console.error("Error fetching customer details:", error);
            } finally {
                setLoading(false);
            }
        };

        if (customerId) fetchCustomer();
    }, [customerId]);

    const handleClose = () => {
        window.history.back(); // Go back to the previous page
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    if (!customer) {
        return <p>Customer not found.</p>;
    }

    return <CustomerDetails customer={customer} onClose={handleClose} />;
}