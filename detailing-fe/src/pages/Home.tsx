import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AppRoutePath } from "../routes/path.routes";
import {
  Navbar,
  Container,
  Nav,
  NavDropdown,
  Button,
  Spinner,
} from "react-bootstrap";
import { useAuth0 } from "@auth0/auth0-react";
import "./Home.css";
import Urus from "./Images/Urus.png";
import Gwagon from "./Images/Gwagon.png";
import { NavBar } from "../nav/NavBar";
import axios from "axios";

interface CustomerInfo {
  customerId: string;
  customerFirstName: string;
  customerLastName: string;
  customerEmailAddress: string;
  streetAddress: string;
  city: string;
  postalCode: string;
  province: string;
  country: string;
}

export default function Home(): JSX.Element {
  const {
    getAccessTokenSilently,
    user,
    isAuthenticated,
    isLoading,
    loginWithRedirect,
  } = useAuth0();
  const [accessToken, setAccessToken] = useState<string | null>(null);
  const [customerInfo, setCustomerInfo] = useState<CustomerInfo | null>(null);
  const navigate = useNavigate();

  const fetchAccessToken = async () => {
    try {
      const token = await getAccessTokenSilently();
      setAccessToken(token);
    } catch (e: any) {
      if (e.error === "consent_required" || e.error === "login_required") {
        await loginWithRedirect();
      }
    }
  };

  const createOrLinkCustomer = async () => {
    if (!isAuthenticated) return;

    try {
      const auth0Sub = user?.sub;
      const storedFormData = localStorage.getItem("customFormData");

      if (storedFormData && auth0Sub) {
        const formData = JSON.parse(storedFormData);
        const token = await getAccessTokenSilently();

        await axios.post(
          "https://highend-zke6.onrender.com/api/customers",
          { ...formData, auth0Sub },
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          },
        );

        localStorage.removeItem("customFormData");
      }
    } catch (error) {
      // Error handling
    }
  };

  const fetchCustomerInfo = async () => {
    if (!accessToken) return;

    try {
      const response = await axios.get<CustomerInfo>(
        "https://highend-zke6.onrender.com/api/customers/me",
        {
          headers: { Authorization: `Bearer ${accessToken}` },
        },
      );
      setCustomerInfo(response.data);
    } catch (error) {
      // Error handling
    }
  };

  useEffect(() => {
    const handleUserData = async () => {
      if (!isAuthenticated) return;

      await createOrLinkCustomer();
      await fetchCustomerInfo();
    };

    handleUserData();
  }, [isAuthenticated, getAccessTokenSilently, user]);

  useEffect(() => {
    const fetchInfo = async () => {
      if (accessToken && isAuthenticated) {
        await fetchCustomerInfo();
      }
    };
    fetchInfo();
  }, [accessToken, isAuthenticated]);

  return (
    <div>
      <NavBar />
      <div className="home-container">
        <h1>Our Mission & Vision</h1>
        <div className="mission-vision-section">
          <div className="image-container">
            <img src={Urus} alt="Car Detailing" />
          </div>
          <div className="text-container">
            <h2>Our Mission</h2>
            <p>
              Our mission is to provide top-quality car detailing services that
              exceed customer expectations, enhancing every vehicle's appearance
              and value with meticulous attention to detail and exceptional
              customer care.
            </p>
          </div>
        </div>
        <div className="vision-section">
          <div className="text-container">
            <h2>Our Vision</h2>
            <p>
              Our vision is to be the leading choice for car detailing,
              recognized for our dedication to excellence, innovation, and a
              personalized customer experience that sets a new standard in the
              industry.
            </p>
          </div>
          <div className="image-container">
            <img src={Gwagon} alt="G-Wagon Detailing" />
          </div>
        </div>
        <div className="content">
          {isLoading ? (
            <Spinner animation="border" variant="primary" />
          ) : (
            <>
              <button
                onClick={fetchAccessToken}
                className="btn btn-primary mb-3"
              >
                Fetch Access Token
              </button>

              {accessToken && (
                <p>
                  <strong>Access Token:</strong> {accessToken}
                </p>
              )}
              {customerInfo ? (
                <div>
                  <h2>
                    Welcome, {customerInfo.customerFirstName}{" "}
                    {customerInfo.customerLastName}!
                  </h2>
                  <p>Email: {customerInfo.customerEmailAddress}</p>
                  <p>
                    Address: {customerInfo.streetAddress}, {customerInfo.city},{" "}
                    {customerInfo.province}, {customerInfo.country}
                  </p>
                </div>
              ) : (
                <p>No customer info available yet.</p>
              )}
            </>
          )}
        </div>
      </div>
    </div>
  );
}
