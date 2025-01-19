import React, { useEffect, useState } from "react";
import { CustomerModel } from "./dtos/CustomerModel";
import "./AllCustomers.css";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import CustomerDetails from "../pages/CustomerDetails";

export default function AllCustomers(): JSX.Element {
  const [customers, setCustomers] = useState<CustomerModel[]>([]);
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [editingCustomer, setEditingCustomer] = useState<CustomerModel | null>(
      null,
  );
  const [viewingCustomer, setViewingCustomer] = useState<CustomerModel | null>(
      null,
  );

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

  const handleViewCustomer = async (customerId: string) => {
    try {
      const response = await axios.get(
          `http://localhost:8080/api/customers/${customerId}`,
      );
      setViewingCustomer(response.data);
    } catch (error) {
      console.error("Error viewing customer details:", error);
      toast.error("Failed to fetch customer details.", {
        position: "top-right",
        autoClose: 3000,
        theme: "colored",
      });
    }
  };

  const handleCloseView = () => {
    setViewingCustomer(null);
  };

  const handleDeleteCustomer = async (customerId: string) => {
    try {
      await axios.delete(`http://localhost:8080/api/customers/${customerId}`);
      setCustomers(
          customers.filter((customer) => customer.customerId !== customerId),
      );
      toast.success("Customer deleted successfully!", {
        position: "top-right",
        autoClose: 3000,
        theme: "colored",
      });
    } catch (error) {
      console.error("Error deleting customer:", error);
      toast.error("Failed to delete customer. Please try again.", {
        position: "top-right",
        autoClose: 3000,
        theme: "colored",
      });
    }
  };

  const handleEditCustomer = (customer: CustomerModel) => {
    setEditingCustomer(customer);
    setIsEditing(true);
  };

  const handleCloseEdit = () => {
    setIsEditing(false);
    setEditingCustomer(null);
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
                      <button
                          onClick={() => handleDeleteCustomer(customer.customerId)}
                      >
                        Delete
                      </button>
                      <button onClick={() => handleEditCustomer(customer)}>
                        Edit
                      </button>
                      <button onClick={() => handleViewCustomer(customer.customerId)}>
                        View Details
                      </button>
                    </div>
                  </div>
              ))
          )}
        </div>

        {isEditing && editingCustomer && (
            <div className="modal-overlay">
              <div className="modal">
                <h3>Edit Customer</h3>
                {/* Edit form here */}
                <button onClick={handleCloseEdit}>Close</button>
              </div>
            </div>
        )}

        {viewingCustomer && (
            <CustomerDetails
                customer={viewingCustomer}
                onClose={handleCloseView}
            />
        )}
      </div>
  );
}