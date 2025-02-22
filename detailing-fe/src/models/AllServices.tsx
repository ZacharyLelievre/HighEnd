import { ServiceModel } from "./dtos/ServiceModel";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import Modal from "react-modal";
import "./AllServices.css";
import { useAuth0 } from "@auth0/auth0-react";

interface PromotionModel {
  promotionId: number;
  serviceId: string; // no "service" nested object
  oldPrice: number;
  newPrice: number;
  discountMessage: string;
}

const customStyles = {
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
  },
};

export default function AllServices(): JSX.Element {
  const { getAccessTokenSilently, getIdTokenClaims } = useAuth0();
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;
  // imageBaseUrl is assumed to be like "http://localhost:8081"
  const imageBaseUrl = process.env.REACT_APP_IMAGE_BASE_URL;
  const [services, setServices] = useState<ServiceModel[]>([]);
  const [promotions, setPromotions] = useState<PromotionModel[]>([]);
  const [modalIsOpen, setModalIsOpen] = useState<boolean>(false);
  const [selectedService, setSelectedService] = useState<ServiceModel | null>(null);
  const [appointmentData, setAppointmentData] = useState({
    appointmentDate: "",
    appointmentTime: "",
    appointmentEndTime: "",
    customerId: "",
    customerName: "",
    employeeId: "",
    employeeName: "",
    serviceId: "",
    serviceName: "",
  });

  // Helper: if imagePath already is a full URL (e.g. added images), return as is;
  // otherwise, assume it's an initial image and prepend the base URL.
  const getImageUrl = (imagePath: string): string => {
    if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
      return imagePath;
    }
    // Ensure we have a leading slash
    const path = imagePath.startsWith("/") ? imagePath : `/${imagePath}`;
    return `${imageBaseUrl}${path}`;
  };

  useEffect(() => {
    const fetchServices = async (): Promise<void> => {
      try {
        const response = await axios.get(`${apiBaseUrl}/services`);
        setServices(response.data);
      } catch (error) {
        console.error("Error fetching services:", error);
      }
    };
    const fetchPromotions = async () => {
      try {
        const response = await axios.get<PromotionModel[]>(`${apiBaseUrl}/promotions`);
        setPromotions(response.data);
      } catch (error) {
        console.error("Error fetching promotions:", error);
      }
    };
    const logUserRoles = async () => {
      try {
        const claims = await getIdTokenClaims();
        if (claims) {
          const roles = claims["https://highenddetailing/roles"];
          console.log("User Roles:", roles);
        } else {
          console.log("No claims found.");
        }
      } catch (error) {
        console.error("Error fetching ID token claims:", error);
      }
    };

    fetchServices();
    fetchPromotions();
    logUserRoles();
  }, [getIdTokenClaims, apiBaseUrl]);

  const openModal = (service: ServiceModel) => {
    setSelectedService(service);
    setAppointmentData({
      ...appointmentData,
      serviceId: service.serviceId,
      serviceName: service.serviceName,
    });
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setModalIsOpen(false);
  };

  const handleInputChange = (
      e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setAppointmentData({ ...appointmentData, [name]: value });
  };

  const handleFormSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const token = await getAccessTokenSilently();
      const response = await axios.post(
          `${apiBaseUrl}/appointments`,
          appointmentData,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
      );
      if (response.status === 201) {
        alert("Appointment booked successfully!");
        closeModal();
        setAppointmentData({
          appointmentDate: "",
          appointmentTime: "",
          appointmentEndTime: "",
          customerId: "",
          customerName: "",
          employeeId: "",
          employeeName: "",
          serviceId: "",
          serviceName: "",
        });
      }
    } catch (error) {
      console.error("Error booking appointment:", error);
      alert("Failed to book appointment. Please try again.");
    }
  };

  function getDisplayedPrice(service: ServiceModel): JSX.Element {
    const promo = promotions.find((p) => p.serviceId === service.serviceId);
    if (!promo) {
      return <p className="service-price">${service.price.toFixed(2)}</p>;
    }
    return (
        <div style={{ textAlign: "center" }}>
          <p
              style={{
                textDecoration: "line-through",
                color: "red",
                margin: 0,
                fontSize: "0.95rem",
              }}
          >
            ${promo.oldPrice.toFixed(2)}
          </p>
          <p
              style={{
                color: "white",
                margin: 0,
                fontWeight: "bold",
              }}
          >
            ${promo.newPrice.toFixed(2)}
          </p>
        </div>
    );
  }

  return (
      <div style={{ backgroundColor: "black" }}>
        <h2 style={{ textAlign: "center", color: "white" }}>Services</h2>
        <div className="services-container">
          {services.map((service) => (
              <div className="service-card" key={service.serviceId}>
                <Link to={`/services/${service.serviceId}`} className="service-link">
                  <div className="service-card-content">
                    <img
                        className="service-image2"
                        src={getImageUrl(service.imagePath)}
                        alt={service.serviceName}
                    />
                    <h3 className="service-name">{service.serviceName}</h3>
                    {getDisplayedPrice(service)}
                  </div>
                </Link>
                {/* You can add a Book Appointment button here if needed */}
              </div>
          ))}
        </div>
        <Modal
            isOpen={modalIsOpen}
            onRequestClose={closeModal}
            style={customStyles}
            contentLabel="Book Appointment Modal"
        >
          <h2>Book Appointment</h2>
          {/* Appointment form goes here */}
        </Modal>
      </div>
  );
}