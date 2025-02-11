import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { ServiceModel } from "./dtos/ServiceModel";
import "./ServiceDetails.css";

export default function ServiceDetail(): JSX.Element {
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;

  const { serviceId } = useParams<{ serviceId: string }>();
  const [service, setService] = useState<ServiceModel | null>(null);
  const [isLoading, setIsLoading] = useState(true); // Local loading state

  useEffect(() => {
    const fetchService = async (): Promise<void> => {
      try {
        const response = await axios.get(
          `${apiBaseUrl}/services/${serviceId}`,
        );
        setService(response.data);
      } catch (error) {
        console.error("Error fetching service details:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchService();
  }, [serviceId]);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!service) {
    return <div>Service not found.</div>;
  }

  return (
    <div className="service-details-container">
      <div className="service-details">
        <div className="service-image-wrapper">
          <img
            className="service-image"
            src={`/${service.imagePath}`}
            alt={service.serviceName}
          />
        </div>
        <div className="service-info">
          <h1>{service.serviceName}</h1>
          <h2>${service.price.toFixed(2)}</h2>
          <ul className="service-description">
            <li>Vacuum cleaning of the entire interior</li>
            <li>Cleaning of door contours</li>
            <li>Deep cleaning of carpets</li>
            <li>Seat cleaning</li>
            <li>Cleaning of door frames, trunk, and sinks</li>
            <li>Cleaning of interior windows</li>
            <li>Meticulous cleaning of the dashboard</li>
            <li>Cleaning of the center console</li>
          </ul>
        </div>
      </div>
    </div>
  );
}
