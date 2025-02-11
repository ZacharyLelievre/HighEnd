import React, { useEffect, useState } from "react";
import { CustomerModel } from "./dtos/CustomerModel";
import "./AllCustomers.css";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";

export default function AllCustomers(): JSX.Element {
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;

  const [customers, setCustomers] = useState<CustomerModel[]>([]);
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [editingCustomer, setEditingCustomer] = useState<CustomerModel | null>(
    null,
  );
  const navigate = useNavigate();

  const provinces = [
    "Alberta",
    "British Columbia",
    "Manitoba",
    "New Brunswick",
    "Newfoundland and Labrador",
    "Nova Scotia",
    "Ontario",
    "Prince Edward Island",
    "Quebec",
    "Saskatchewan",
  ];
  const countries = ["Canada", "United States", "Mexico"];

  useEffect(() => {
    const fetchCustomers = async (): Promise<void> => {
      try {
        const response = await axios.get(`${apiBaseUrl}/customers`);
        setCustomers(response.data);
      } catch (error) {
        console.error("Error fetching customers:", error);
      }
    };

    fetchCustomers();
  }, []);

  const handleDeleteCustomer = async (customerId: string) => {
    try {
      await axios.delete(`${apiBaseUrl}/customers/${customerId}`);
      setCustomers(
        customers.filter((customer) => customer.customerId !== customerId),
      );
      toast.success("Customer deleted successfully!", {
        position: "top-right",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        theme: "colored",
      });
    } catch (error) {
      console.error("Error deleting customer:", error);
      toast.error("Failed to delete customer. Please try again.", {
        position: "top-right",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        theme: "colored",
      });
    }
  };

  const handleEditCustomer = (customer: CustomerModel) => {
    setEditingCustomer(customer);
    setIsEditing(true);
  };

  const handleSubmitEdit = async (event: React.FormEvent) => {
    event.preventDefault();
    if (editingCustomer) {
      try {
        await axios.put(
          `${apiBaseUrl}/customers/${editingCustomer.customerId}`,
          editingCustomer,
        );
        setCustomers(
          customers.map((customer) =>
            customer.customerId === editingCustomer.customerId
              ? editingCustomer
              : customer,
          ),
        );
        setIsEditing(false);
        setEditingCustomer(null);
      } catch (error) {
        console.error("Error updating customer:", error);
      }
    }
  };

  const handleChange = (
    event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    if (editingCustomer) {
      const { name, value } = event.target;
      setEditingCustomer({
        ...editingCustomer,
        [name]: value,
      });
    }
  };

  const handleCloseModal = () => {
    setIsEditing(false);
    setEditingCustomer(null);
  };
  const handleViewCustomer = (customerId: string): void => {
    navigate(`/customers/${customerId}`); // Navigate to CustomerDetails
  };

  return (
    <div className="container">
      <ToastContainer />
      <div className="customers-container">
        {customers.length === 0 ? (
          <p className="no-customers-message">No customers found.</p>
        ) : (
          customers.map((customer) => (
            <div className="customer-box" key={customer.customerId}>
              <div className="customer-details">
                <h2 style={{ textAlign: "left" }}>
                  {customer.customerFirstName} {customer.customerLastName}
                </h2>
                <p>
                  <strong>Email:</strong> {customer.customerEmailAddress}
                </p>
                <p>
                  <strong>Address:</strong> {customer.streetAddress},{" "}
                  {customer.city}, {customer.postalCode}, {customer.province},{" "}
                  {customer.country}
                </p>
                <button
                  onClick={() => handleDeleteCustomer(customer.customerId)}
                >
                  Delete
                </button>
                <button onClick={() => handleEditCustomer(customer)}>
                  Edit
                </button>
                <button onClick={() => handleViewCustomer(customer.customerId)}>
                  View
                </button>
              </div>
            </div>
          ))
        )}
      </div>

      {isEditing && editingCustomer && (
        <div className="modal-overlay">
          <div className="modal">
            <div className="modal-header">
              <h3>Edit Customer</h3>
              <button className="close-btn" onClick={handleCloseModal}>
                X
              </button>
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
                <select
                  name="province"
                  value={editingCustomer.province || ""}
                  onChange={handleChange}
                >
                  <option value="">Select a province</option>
                  {provinces.map((province) => (
                    <option key={province} value={province}>
                      {province}
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <label>Country:</label>
                <select
                  name="country"
                  value={editingCustomer.country || ""}
                  onChange={handleChange}
                >
                  <option value="">Select a country</option>
                  {countries.map((country) => (
                    <option key={country} value={country}>
                      {country}
                    </option>
                  ))}
                </select>
              </div>
              <button type="submit">Save Changes</button>
              <button type="button" onClick={handleCloseModal}>
                Cancel
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
