import React, { useEffect, useState } from "react";
import { CustomerModel } from "./dtos/CustomerModel";
import "./AllCustomers.css";
import axios from "axios";

export default function AllCustomers(): JSX.Element {
    const [customers, setCustomers] = useState<CustomerModel[]>([]);

    useEffect(() => {
        const fetchCustomers = async (): Promise<void> => {
            try {
                const response = await axios.get("http://localhost:8080/api/customers");
                setCustomers(response.data);
            } catch (error) {
                console.error("Error fetching customers:", error);
            }
        };

        fetchCustomers();
    }, []);

    return (
        <div>
            <h2 style={{ textAlign: "center" }}>Customers</h2>
            <div className="customers-container">
                {customers.map(customer => (
                    <div className="customer-box" key={customer.customerId}>
                        <div className="customer-details">
                            <p><strong>Name:</strong> {customer.customerFirstName} {customer.customerLastName}</p>
                            <p><strong>Email:</strong> {customer.customerEmailAddress}</p>
                            <p><strong>Address:</strong> {customer.streetAddress}, {customer.city}, {customer.postalCode}, {customer.province}, {customer.country}</p>
                            <button>View</button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}