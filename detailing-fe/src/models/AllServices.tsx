import { ServiceModel } from "./dtos/ServiceModel";
import React, { useEffect, useState } from "react";
import axios from "axios";
import "./AllServices.css"; // Import the CSS for styling

export default function AllServices(): JSX.Element {
    const [services, setServices] = useState<ServiceModel[]>([]);

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

    return (
        <div>
            <h2 style={{textAlign: "center"}}> Services </h2>
            <div className="services-container">
                {services.map(service => (
                    <div className="service-card" key={service.serviceId}>
                        <img
                            className="service-image"
                            src={`http://localhost:8080/${service.imagePath}`}
                            alt={service.serviceName}
                        />
                        <h3 className="service-name">{service.serviceName}</h3>
                        <p className="service-price">${service.price.toFixed(2)}</p>
                    </div>
                ))}
            </div>

        </div>
    );
}
