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
import { useTranslation } from "react-i18next";

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

  const { t, i18n } = useTranslation(); // Initialize i18next translation hook
  const handleLanguageChange = (lang: string) => {
    i18n.changeLanguage(lang); // Change language dynamically
  };

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

  // Dynamically load Elfsight Google Reviews API script
  useEffect(() => {
    const script = document.createElement("script");
    script.src = "https://static.elfsight.com/platform/platform.js";
    script.async = true;
    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script);
    };
  }, []);

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
            <h2>{t("hero_title")}</h2>
            <p>{t("hero_description")}</p>
            <Button
              className="video-button"
              onClick={() => navigate(AppRoutePath.AllServicesPage)}
            >
              <strong>{t("explore_services")}</strong>
            </Button>
          </motion.div>
        </div>
        {/* NavBar placed at the top of the hero section */}
        <div className="nav-container">
          <NavBar />
          {/* Language Switcher */}
          {/* <div className="language-switcher">
          <button onClick={() => handleLanguageChange('en')}>English</button>
          <button onClick={() => handleLanguageChange('fr')}>Français</button>
        </div> */}
        </div>
      </div>

      {/* Main Content below the hero */}
      <div className="home-container">
        <motion.h1
          initial={{ opacity: 0, scale: 0.8 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ duration: 0.5 }}
        >
          {t("mission_vision")}
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
            <h2>{t("our_mission")}</h2>
            <p>{t("mission_description")}</p>
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
            <h2>{t("our_vision")}</h2>
            <p>{t("vision_description")}</p>
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

        {/* Google Reviews widget */}
        <div className="google-reviews-widget" style={{ paddingTop: "30px" }}>
          <div
            className="elfsight-app-38317100-95ae-480a-9fbd-3dc4a5267fc2"
            data-elfsight-app-lazy
          ></div>
        </div>
      </div>

      <Footer />
    </div>
  );
}
