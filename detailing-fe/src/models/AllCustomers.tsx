import React, { useEffect, useState } from "react";
import { CustomerModel } from "./dtos/CustomerModel";
import "./AllCustomers.css";
import axios from "axios";

export default function AllCustomers(): JSX.Element {
    const [customers, setCustomers] = useState<CustomerModel[]>([]);
    const [isEditing, setIsEditing] = useState<boolean>(false);
    const [editingCustomer, setEditingCustomer] = useState<CustomerModel | null>(null);

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

    const handleEditCustomer = (customer: CustomerModel) => {
        setEditingCustomer(customer);
        setIsEditing(true);
    }

    const handleSubmitEdit = async (event: React.FormEvent) => {
        event.preventDefault();
        if (editingCustomer) {
            try {
                await axios.put(`http://localhost:8080/api/customers/${editingCustomer.customerId}`, editingCustomer);
                setCustomers(customers.map((customer) =>
                    customer.customerId === editingCustomer.customerId ? editingCustomer : customer
                ));
                setIsEditing(false);
                setEditingCustomer(null);
            } catch (error) {
                console.error("Error updating customer:", error);
            }
        }
    }

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (editingCustomer) {
            const { name, value } = event.target;
            setEditingCustomer({
                ...editingCustomer,
                [name]: value
            });
        }
    }

    const handleCloseModal = () => {
        setIsEditing(false);
        setEditingCustomer(null);
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
                            <button onClick={() => handleEditCustomer(customer)}>Edit</button>
                        </div>
                    </div>
                ))}
            </div>

            {isEditing && editingCustomer && (
                <div className="modal-overlay">
                    <div className="modal">
                        <div className="modal-header">
                            <h3>Edit Customer</h3>
                            <button className="close-btn" onClick={handleCloseModal}>X</button>
                        </div>
                        <form onSubmit={handleSubmitEdit}>
                            <div>
                                <label>First Name:</label>
                                <input
                                    type="text"
                                    name="customerFirstName"
                                    value={editingCustomer.customerFirstName || ""}
                                    onChange={handleChange}
                                />
                            </div>
                            <div>
                                <label>Last Name:</label>
                                <input
                                    type="text"
                                    name="customerLastName"
                                    value={editingCustomer.customerLastName || ""}
                                    onChange={handleChange}
                                />
                            </div>
                            <div>
                                <label>Email:</label>
                                <input
                                    type="email"
                                    name="customerEmailAddress"
                                    value={editingCustomer.customerEmailAddress || ""}
                                    onChange={handleChange}
                                />
                            </div>
                            <div>
                                <label>Street Address:</label>
                                <input
                                    type="text"
                                    name="streetAddress"
                                    value={editingCustomer.streetAddress || ""}
                                    onChange={handleChange}
                                />
                            </div>
                            <div>
                                <label>City:</label>
                                <input
                                    type="text"
                                    name="city"
                                    value={editingCustomer.city || ""}
                                    onChange={handleChange}
                                />
                            </div>
                            <div>
                                <label>Postal Code:</label>
                                <input
                                    type="text"
                                    name="postalCode"
                                    value={editingCustomer.postalCode || ""}
                                    onChange={handleChange}
                                />
                            </div>
                            <div>
                                <label>Province:</label>
                                <input
                                    type="text"
                                    name="province"
                                    value={editingCustomer.province || ""}
                                    onChange={handleChange}
                                />
                            </div>
                            <div>
                                <label>Country:</label>
                                <input
                                    type="text"
                                    name="country"
                                    value={editingCustomer.country || ""}
                                    onChange={handleChange}
                                />
                            </div>
                            <button type="submit">Save Changes</button>
                            <button type="button" onClick={handleCloseModal}>Cancel</button>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}