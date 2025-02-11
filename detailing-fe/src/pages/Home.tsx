import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AppRoutePath } from "../routes/path.routes";
import { Button } from "react-bootstrap";
import { useAuth0 } from "@auth0/auth0-react";
import "./Home.css";
import Urus from "./Images/Urus.png";
import Gwagon from "./Images/Gwagon.png";
import { NavBar } from "../nav/NavBar";
import axios from "axios";
import { motion } from "framer-motion";
import { Footer } from "./Footer";

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
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;

  const { getAccessTokenSilently, user, isAuthenticated, loginWithRedirect } =
    useAuth0();
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
          `${apiBaseUrl}/customers`,
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
        `${apiBaseUrl}/customers/me`,
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
    if (accessToken && isAuthenticated) {
      fetchCustomerInfo();
    }
  }, [accessToken, isAuthenticated]);

  return (
    <div className="home-page">
      <div className="hero">
        <div className="video-wrapper">
          <iframe
            title="Highend Detailing Loop Video"
            src="https://player.vimeo.com/video/854048415?h=2b2e4feb91&autoplay=1&loop=1&muted=1&title=0&byline=0&portrait=0&controls=0"
            frameBorder="0"
            allow="autoplay; fullscreen"
            allowFullScreen
            className="vimeo-iframe"
          ></iframe>
          {/* Centered overlay message and button */}
          <motion.div
            className="video-overlay"
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8, delay: 0.5 }}
          >
            <h2>Rev Up Your Ride!</h2>
            <p>
              Discover our premium car detailing services that make your ride
              shine.
            </p>
            <Button
              className="video-button"
              onClick={() => navigate(AppRoutePath.AllServicesPage)}
            >
              Explore Services
            </Button>
          </motion.div>
        </div>
        {/* NavBar placed at the top of the hero section */}
        <div className="nav-container">
          <NavBar />
        </div>
      </div>

      {/* Main Content below the hero */}
      <div className="home-container">
        <motion.h1
          initial={{ opacity: 0, scale: 0.8 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ duration: 0.5 }}
        >
          Our Mission & Vision
        </motion.h1>

        <motion.div
          className="mission-vision-section"
          initial={{ opacity: 0, x: -50 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 0.6 }}
        >
          <motion.div
            className="image-container"
            initial={{ opacity: 0, scale: 0.9 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.5 }}
          >
            <motion.img
              src={Urus}
              alt="Car Detailing"
              whileHover={{ scale: 1.05 }}
            />
          </motion.div>
          <motion.div
            className="text-container"
            initial={{ opacity: 0, x: 50 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.6 }}
          >
            <h2>Our Mission</h2>
            <p>
              Our mission is to provide top-quality car detailing services that
              exceed customer expectations, enhancing every vehicle's appearance
              and value with meticulous attention to detail and exceptional
              customer care.
            </p>
          </motion.div>
        </motion.div>

        <motion.div
          className="vision-section"
          initial={{ opacity: 0, x: 50 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 0.6, delay: 0.3 }}
        >
          <motion.div
            className="text-container"
            initial={{ opacity: 0, x: -50 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.6 }}
          >
            <h2>Our Vision</h2>
            <p>
              Our vision is to be the leading choice for car detailing,
              recognized for our dedication to excellence, innovation, and a
              personalized customer experience that sets a new standard in the
              industry.
            </p>
          </motion.div>
          <motion.div
            className="image-container"
            initial={{ opacity: 0, scale: 0.9 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.5 }}
          >
            <motion.img
              src={Gwagon}
              alt="G-Wagon Detailing"
              whileHover={{ scale: 1.05 }}
            />
          </motion.div>
        </motion.div>
      </div>
      <Footer />
    </div>
  );
}
