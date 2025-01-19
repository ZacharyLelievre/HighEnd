import React, { useState } from "react";
import axios from "axios";
import { CustomerModel } from "./dtos/CustomerModel";

interface EditCustomerFormProps {
  customer: CustomerModel;
  onUpdate: () => void;
}

const [customers, setCustomers] = useState<CustomerModel[]>([]);

function EditCustomerForm({ customer, onUpdate }: EditCustomerFormProps) {
  const [formData, setFormData] = useState({
    customerFirstName: customer.customerFirstName,
    customerLastName: customer.customerLastName,
    customerEmailAddress: customer.customerEmailAddress,
    streetAddress: customer.streetAddress,
    city: customer.city,
    postalCode: customer.postalCode,
    province: customer.province,
    country: customer.country,
  });

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [event.target.name]: event.target.value });
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const response = await axios.put(
        `https://highend-zke6.onrender.com/api/customers/${customer.customerId}`,
        formData,
      );

      // Update local state if successful (optional)
      setCustomers(
        customers.map((c) =>
          c.customerId === customer.customerId ? response.data : c,
        ),
      );

      onUpdate(); // Close the edit form
    } catch (error) {
      console.error("Error updating customer:", error);
      // Handle errors, e.g., display an error message to the user
      alert("Error updating customer. Please try again later.");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="firstName">First Name:</label>
        <input
          type="text"
          id="firstName"
          name="customerFirstName"
          value={formData.customerFirstName}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="lastName">Last Name:</label>
        <input
          type="text"
          id="lastName"
          name="customerLastName"
          value={formData.customerLastName}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          name="customerEmailAddress"
          value={formData.customerEmailAddress}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="streetAddress">Street Address:</label>
        <input
          type="text"
          id="streetAddress"
          name="streetAddress"
          value={formData.streetAddress}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="city">City:</label>
        <input
          type="text"
          id="city"
          name="city"
          value={formData.city}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="postalCode">Postal Code:</label>
        <input
          type="text"
          id="postalCode"
          name="postalCode"
          value={formData.postalCode}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="province">Province:</label>
        <input
          type="text"
          id="province"
          name="province"
          value={formData.province}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="country">Country:</label>
        <input
          type="text"
          id="country"
          name="country"
          value={formData.country}
          onChange={handleChange}
        />
      </div>
      <div>
        <button type="submit">Update</button>
        <button type="button" onClick={onUpdate}>
          Cancel
        </button>
      </div>
    </form>
  );
}

export default EditCustomerForm;
