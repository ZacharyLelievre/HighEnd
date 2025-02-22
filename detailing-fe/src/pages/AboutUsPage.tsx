import React from "react";
import "./AboutUsPage.css";
import { NavBar } from "../nav/NavBar";
import { Footer } from "./Footer";
import { useTranslation } from "react-i18next";
import renaud from "./Images/renaud.png";
import Eunho from "./Images/Eunho.png";

export default function AboutUsPage(): JSX.Element {
  const { t } = useTranslation();

  return (
    <div className="about-page">
      <NavBar />
      <div className="about-page-container">
        {/* Hero Section */}
        <section className="hero-section">
          <div className="hero-content">
            <h1>{t("about_title")}</h1>
            <p>{t("about_intro")}</p>
          </div>
        </section>

        {/* Founders Section */}
        <section className="founders-section">
          <div className="founder-card">
            <img src={renaud} alt="Renaud" className="founder-image" />
            <h2 className="founder-name">{t("founder1_name")}</h2>
            <h3 className="founder-subtitle">{t("founder1_subtitle")}</h3>
            <p>{t("founder1_description")}</p>
          </div>

          <div className="founder-card">
            <img src={Eunho} alt="Eunho" className="founder-image" />
            <h2 className="founder-name">{t("founder2_name")}</h2>
            <h3 className="founder-subtitle">{t("founder2_subtitle")}</h3>
            <p>{t("founder2_description")}</p>
          </div>
        </section>

        {/* Story / History Section */}
        <section className="story-section">
          <h2>{t("our_history_heading")}</h2>
          <p>{t("history_paragraph1")}</p>
          <p>{t("history_paragraph2")}</p>
          <p>{t("history_paragraph3")}</p>
          <p>{t("history_paragraph4")}</p>
        </section>

        {/* Contact Section */}
        <section className="contact-section">
          <h2>{t("contact_title")}</h2>
          <div className="contact-details">
            <div className="contact-info">
              <h3>{t("contact_call_heading")}</h3>
              <p>{t("contact_phone1")}</p>
              <p>{t("contact_phone2")}</p>
            </div>
            <div className="contact-info">
              <h3>{t("contact_email_heading")}</h3>
              <p>{t("contact_email1")}</p>
              <p>{t("contact_email2")}</p>
            </div>
            <div className="contact-info">
              <h3>{t("contact_hours_heading")}</h3>
              <p>{t("contact_hours_monday")}</p>
              <p>{t("contact_hours_tuesday")}</p>
              <p>{t("contact_hours_wednesday")}</p>
              <p>{t("contact_hours_thursday")}</p>
              <p>{t("contact_hours_friday")}</p>
              <p>{t("contact_hours_saturday")}</p>
              <p>{t("contact_hours_sunday")}</p>
            </div>
          </div>
        </section>

        <Footer />
      </div>
    </div>
  );
}
