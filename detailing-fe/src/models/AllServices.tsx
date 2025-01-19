import { ServiceModel } from "./dtos/ServiceModel";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import Modal from "react-modal";
import "./AllServices.css";

// Custom styles for the modal
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
  const [services, setServices] = useState<ServiceModel[]>([]);
  const [modalIsOpen, setModalIsOpen] = useState<boolean>(false);
  const [selectedService, setSelectedService] = useState<ServiceModel | null>(
    null,
  );
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

  useEffect(() => {
    const fetchServices = async (): Promise<void> => {
      try {
        const response = await axios.get("http://localhost:8080/api/services");
        setServices(response.data);
      } catch (error) {
        console.error("Error fetching services:", error);
      }
    };

    fetchServices();
  }, []);

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
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    const { name, value } = e.target;
    setAppointmentData({ ...appointmentData, [name]: value });
  };

  const handleFormSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/appointments",
        appointmentData,
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

  return (
    <div>
      <h2 style={{ textAlign: "center" }}>Services</h2>
      <div className="services-container">
        {services.map((service) => (
          <div className="service-card" key={service.serviceId}>
            <Link
              to={`/services/${service.serviceId}`}
              className="service-link"
            >
              <img
                className="service-image"
                src={`http://localhost:8080/${service.imagePath}`}
                alt={service.serviceName}
              />
              <h3 className="service-name">{service.serviceName}</h3>
              <p className="service-price">${service.price.toFixed(2)}</p>
            </Link>
            <button onClick={() => openModal(service)}>Book Appointment</button>
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
        <button onClick={closeModal}>Close</button>
        <form onSubmit={handleFormSubmit}>
          <input
            type="date"
            name="appointmentDate"
            value={appointmentData.appointmentDate}
            onChange={handleInputChange}
            required
          />
          <input
            type="time"
            name="appointmentTime"
            value={appointmentData.appointmentTime}
            onChange={handleInputChange}
            required
          />
          <input
            type="time"
            name="appointmentEndTime"
            value={appointmentData.appointmentEndTime}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="customerId"
            placeholder="Customer ID"
            value={appointmentData.customerId}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="customerName"
            placeholder="Customer Name"
            value={appointmentData.customerName}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="employeeId"
            placeholder="Employee ID"
            value={appointmentData.employeeId}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="employeeName"
            placeholder="Employee Name"
            value={appointmentData.employeeName}
            onChange={handleInputChange}
            required
          />
          <button type="submit">Book Now</button>
        </form>
      </Modal>
    </div>
  );
}
