// src/pages/Home.tsx
// (Parts are the same, but note the "createOrLinkEmployee()" addition)

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

  // ---- NEW FUNCTION: createOrLinkEmployee
  const createOrLinkEmployee = async () => {
    if (!isAuthenticated) return;

    try {
      const auth0Sub = user?.sub;
      const storedFormData = localStorage.getItem("employeeFormData");

      if (storedFormData && auth0Sub) {
        // parse the localStorage data
        const formData = JSON.parse(storedFormData);
        const token = await getAccessTokenSilently();

        // POST to your employees endpoint
        await axios.post(
            "https://highend-zke6.onrender.com/api/employees",
            { ...formData, auth0Sub },  // your EmployeeRequestModel
            {
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
              },
            },
        );
        localStorage.removeItem("employeeFormData");
      }
    } catch (error) {
      console.error("Error linking/creating employee:", error);
    }
  };

  // For customers (unchanged)
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
      console.error("Error linking/creating customer:", error);
    }
  };

  // same as before: fetchAccessToken
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

  // same as before: fetchCustomerInfo
  const fetchCustomerInfo = async () => {
    if (!accessToken) return;
    try {
      const response = await axios.get<CustomerInfo>(
          "https://highend-zke6.onrender.com/api/customers/me",
          { headers: { Authorization: `Bearer ${accessToken}` } },
      );
      setCustomerInfo(response.data);
    } catch (error) {
      console.error("Error fetching customer info:", error);
    }
  };

  // On first load (or when user changes),
  // run both createOrLinkCustomer and createOrLinkEmployee, then fetch info
  useEffect(() => {
    const handleUserData = async () => {
      if (!isAuthenticated) return;
      // 1) create/link new user if they had just done sign-up
      await createOrLinkCustomer();
      await createOrLinkEmployee();
      // 2) fetch customer info if we want to display it on homepage
      await fetchAccessToken();
    };
    handleUserData();
  }, [isAuthenticated, user]);

  // Whenever accessToken changes, re-fetch the profile
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

          {/* Your same UI layout */}
          <div className="mission-vision-section">
            <div className="image-container">
              <img src={Urus} alt="Car Detailing" />
            </div>
            <div className="text-container">
              <h2>Our Mission</h2>
              <p>
                Our mission is to provide top-quality car detailing ...
              </p>
            </div>
          </div>

          <div className="vision-section">
            <div className="text-container">
              <h2>Our Vision</h2>
              <p>
                Our vision is to be the leading choice ...
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
                      <div className="access-token-box">
                        <strong>Access Token:</strong> {accessToken}
                      </div>
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