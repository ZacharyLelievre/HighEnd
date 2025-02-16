import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { NavBar } from "../nav/NavBar";

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

interface PromotionModel {
  promotionId: string;
  serviceId: string; // ✅ Added serviceId
  serviceName?: string; // This will be assigned dynamically
  newPrice: number;
  discountMessage: string;
}

export default function PromotionForm() {
  const [services, setServices] = useState<ServiceModel[]>([]);
  const [promotions, setPromotions] = useState<PromotionModel[]>([]);
  const [selectedServiceId, setSelectedServiceId] = useState("");
  const [newPrice, setNewPrice] = useState("");
  const [discountMessage, setDiscountMessage] = useState("");

  const apiBaseUrl =
    process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";
  const navigate = useNavigate();

  // Fetch services and promotions on mount
  useEffect(() => {
    const fetchServicesAndPromotions = async () => {
      try {
        // Fetch services first
        const serviceRes = await axios.get<ServiceModel[]>(
          `${apiBaseUrl}/services`,
        );
        setServices(serviceRes.data);

        // Then fetch promotions
        const promoRes = await axios.get<PromotionModel[]>(
          `${apiBaseUrl}/promotions`,
        );

        // Map promotions with correct service names
        const promotionsWithNames = promoRes.data.map((promo) => {
          const matchedService = serviceRes.data.find(
            (service) => service.serviceId === promo.serviceId,
          );
          return {
            ...promo,
            serviceName: matchedService?.serviceName || "Unknown",
          };
        });

        setPromotions(promotionsWithNames);
      } catch (err) {
        console.error("Error fetching services or promotions:", err);
      }
    };

    fetchServicesAndPromotions();
  }, [apiBaseUrl]);

  const fetchPromotions = async () => {
    try {
      const promoRes = await axios.get<PromotionModel[]>(
        `${apiBaseUrl}/promotions`,
      );

      // Ensure services are available before mapping
      const promotionsWithNames = promoRes.data.map((promo) => {
        const matchedService = services.find(
          (service) => service.serviceId === promo.serviceId,
        );
        return {
          ...promo,
          serviceName: matchedService?.serviceName || "Unknown",
        };
      });

      setPromotions(promotionsWithNames);
    } catch (err) {
      console.error("Error fetching promotions:", err);
    }
  };
  // Handle creating a promotion
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
        setSelectedServiceId("");
        setNewPrice("");
        setDiscountMessage("");
        fetchPromotions(); // Refresh promotions list
      }
    } catch (error) {
      console.error("Error creating promotion:", error);
      alert("Failed to create promotion.");
    }
  };

  // Handle deleting a promotion
  const handleRemovePromotion = async (promotionId: string) => {
    if (!window.confirm("Are you sure you want to remove this promotion?"))
      return;
    try {
      await axios.delete(`${apiBaseUrl}/promotions/${promotionId}`);
      alert("Promotion removed successfully.");

      // 1) Fetch updated promotions list
      const updatedPromotions = promotions.filter(
        (p) => p.promotionId !== promotionId,
      );
      setPromotions(updatedPromotions);
    } catch (error) {
      console.error("Error removing promotion:", error);
      alert("Failed to remove promotion.");
    }
  };

  return (
    <div>
      <NavBar />
      <div style={styles.container}>
        {/* ADD PROMOTION SECTION */}
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

          <label style={styles.label}>
            Discount Message (optional):
            <input
              style={styles.input}
              type="text"
              value={discountMessage}
              onChange={(e) => setDiscountMessage(e.target.value)}
            />
          </label>

          <button type="submit" style={styles.button}>
            Submit
          </button>
        </form>

        {/* GET ALL PROMOTIONS SECTION */}
        <h2 style={styles.heading}>Active Promotions</h2>
        {promotions.length === 0 ? (
          <p>No promotions available.</p>
        ) : (
          <table style={styles.table}>
            <thead>
              <tr>
                <th>Service</th>
                <th>New Price</th>
                <th>Discount Message</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {promotions.map((promo) => (
                <tr key={promo.promotionId}>
                  <td>{promo.serviceName}</td> {/* ✅ Fixed Service Name */}
                  <td>${promo.newPrice.toFixed(2)}</td>
                  <td>{promo.discountMessage || "N/A"}</td>
                  <td>
                    <button
                      style={styles.deleteButton}
                      onClick={() => handleRemovePromotion(promo.promotionId)}
                    >
                      Remove
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

const styles: { [key: string]: React.CSSProperties } = {
  container: {
    maxWidth: "600px",
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
  button: {
    padding: "0.75rem",
    backgroundColor: "#333",
    color: "#fff",
    border: "none",
    cursor: "pointer",
  },
  table: {
    width: "100%",
    marginTop: "1rem",
    borderCollapse: "collapse",
  },
  deleteButton: {
    backgroundColor: "red",
    color: "white",
    border: "none",
    padding: "0.5rem",
    cursor: "pointer",
  },
};
