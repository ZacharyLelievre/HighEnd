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
        <div className="service-details">
            <h2>{service.serviceName}</h2>
            <img
                className="service-image"
                src={`http://localhost:8080/${service.imagePath}`}
                alt={service.serviceName}
            />
            <p>{"Description"}</p>
            <p className="service-price">Price: ${service.price.toFixed(2)}</p>
        </div>
    );
}
