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

    const handleDeleteCustomer = async (customerId: string) => {
        try {
            await axios.delete(`http://localhost:8080/api/customers/${customerId}`);
            setCustomers(customers.filter((customer) => customer.customerId !== customerId));
        } catch (error) {
            console.error("Error deleting customer:", error);
        }
    }

    return (
        <div className='container'>
            <div className='customers-container'>
                {customers.map(customer => (
                    <div className="customer-box" key={customer.customerId}>
                        <div className="customer-details">
                            <h2 style={{ textAlign: "left" }}>{customer.customerFirstName} {customer.customerLastName}</h2>
                            <p><strong>Email:</strong> {customer.customerEmailAddress}</p>
                            <p><strong>Address:</strong> {customer.streetAddress}, {customer.city}, {customer.postalCode}, {customer.province}, {customer.country}</p>
                            <button onClick={() => handleDeleteCustomer(customer.customerId)}>Delete</button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}