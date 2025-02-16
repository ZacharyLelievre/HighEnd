import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

interface ServiceModel {
  serviceId: string;
  serviceName: string;
  price: number;
}

interface PromotionRequest {
  serviceId: string;
  newPrice: number;
  discountMessage: string;
}

export default function PromotionForm() {
  const [services, setServices] = useState<ServiceModel[]>([]);
  const [selectedServiceId, setSelectedServiceId] = useState("");
  const [newPrice, setNewPrice] = useState("");
  const [discountMessage, setDiscountMessage] = useState("");

  const apiBaseUrl =
    process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";
  const navigate = useNavigate(); // <-- For redirection

  useEffect(() => {
    axios
      .get<ServiceModel[]>(`${apiBaseUrl}/services`)
      .then((res) => setServices(res.data))
      .catch((err) => console.error("Error fetching services:", err));
  }, [apiBaseUrl]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const promotionData: PromotionRequest = {
        serviceId: selectedServiceId,
        newPrice: parseFloat(newPrice),
        discountMessage,
      };

      const response = await axios.post(
        `${apiBaseUrl}/promotions`,
        promotionData,
      );
      if (response.status === 201) {
        alert("Promotion created successfully!");
        // Reset form
        setSelectedServiceId("");
        setNewPrice("");
        setDiscountMessage("");
        // Redirect to the services page
        navigate("/services");
      }
    } catch (error) {
      console.error("Error creating promotion:", error);
      alert("Failed to create promotion.");
    }
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.heading}>Create a Promotion</h2>
      <form onSubmit={handleSubmit} style={styles.form}>
        <label style={styles.label}>
          Select Service:
          <select
            style={styles.select}
            value={selectedServiceId}
            onChange={(e) => setSelectedServiceId(e.target.value)}
            required
          >
            <option value="">-- Choose a service --</option>
            {services.map((service) => (
              <option key={service.serviceId} value={service.serviceId}>
                {service.serviceName} (Current: ${service.price.toFixed(2)})
              </option>
            ))}
          </select>
        </label>

        <label style={styles.label}>
          New Price:
          <input
            style={styles.input}
            type="number"
            step="0.01"
            value={newPrice}
            onChange={(e) => setNewPrice(e.target.value)}
            required
          />
        </label>
        <button type="submit" style={styles.button}>
          Submit
        </button>
      </form>
    </div>
  );
}

const styles: { [key: string]: React.CSSProperties } = {
  container: {
    maxWidth: "400px",
    margin: "0 auto",
    padding: "1rem",
    backgroundColor: "#fff",
    borderRadius: "8px",
  },
  heading: {
    textAlign: "center",
  },
  form: {
    display: "flex",
    flexDirection: "column",
    gap: "1rem",
  },
  label: {
    display: "flex",
    flexDirection: "column",
    fontWeight: "bold",
  },
  select: {
    marginTop: "0.5rem",
    padding: "0.5rem",
  },
  input: {
    marginTop: "0.5rem",
    padding: "0.5rem",
  },
  textarea: {
    marginTop: "0.5rem",
    padding: "0.5rem",
    minHeight: "60px",
  },
  button: {
    padding: "0.75rem",
    backgroundColor: "#333",
    color: "#fff",
    border: "none",
    cursor: "pointer",
  },
};
