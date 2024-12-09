import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { ServiceModel } from "./dtos/ServiceModel";
import "./ServiceDetails.css";

export default function ServiceDetail(): JSX.Element {
    const { serviceId } = useParams<{ serviceId: string }>();
    const [service, setService] = useState<ServiceModel | null>(null);

    useEffect(() => {
        const fetchService = async (): Promise<void> => {
            try {
                const response = await axios.get(`http://localhost:8080/api/services/${serviceId}`);
                setService(response.data);
            } catch (error) {
                console.error("Error fetching service details:", error);
            }
        };

        fetchService();
    }, [serviceId]);

    if (!service) {
        return <div>Loading...</div>;
    }

    return (
        <div className="service-details-container">
            <div className="service-details">
                <div className="service-image-wrapper">
                    <img
                        className="service-image"
                        src={`http://localhost:8080/${service.imagePath}`}
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
            <div className="service-scheduler">
                <div className="calendar">
                    <h3>Select date</h3>
                    <div className="calendar-ui">[Calendar Component]</div>
                </div>
                <div className="time-slots">
                    <h3>Select Time</h3>
                    <div className="time-slot-buttons">
                        <button>Select Time</button>
                        <button>Select Time</button>
                        <button>Select Time</button>
                    </div>
                </div>
            </div>
        </div>
    );
}
