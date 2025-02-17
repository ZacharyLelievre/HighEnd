import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AppRoutePath } from "../routes/path.routes";
import { Spinner } from "react-bootstrap";
import { useAuth0 } from "@auth0/auth0-react";
import "./NavBar.css";
import { useTranslation } from "react-i18next";

export function NavBar(): JSX.Element {
  const {
    loginWithRedirect,
    logout,
    user,
    isAuthenticated,
    isLoading,
    getAccessTokenSilently,
  } = useAuth0();
  const [isAdmin, setIsAdmin] = useState(false);
  const [menuOpen, setMenuOpen] = useState(false);
  const navigate = useNavigate();

  const { t, i18n } = useTranslation(); // Initialize i18next translation hook
  const handleLanguageChange = (lang: string) => {
    i18n.changeLanguage(lang); // Change language dynamically
  };

  const toggleMenu = () => setMenuOpen(!menuOpen);

  useEffect(() => {
    if (!isAuthenticated) return;

    const checkIfUserIsAdmin = async () => {
      try {
        const accessToken = await getAccessTokenSilently();
        const base64Url = accessToken.split(".")[1];
        const decodedPayload = atob(base64Url);
        const tokenData = JSON.parse(decodedPayload);
        const roles =
          tokenData["https://highenddetailing/roles"] || tokenData.roles || [];
        setIsAdmin(roles.includes("ADMIN"));
      } catch (error) {
        console.error("Error fetching access token or roles:", error);
      }
    };

    checkIfUserIsAdmin();
  }, [isAuthenticated, getAccessTokenSilently]);

  const handleLogin = () => loginWithRedirect();
  const handleRegister = () => navigate(AppRoutePath.Onboarding);
  const handleLogout = () =>
    logout({ logoutParams: { returnTo: window.location.origin } });

  return (
    <header
      className="custom-nav-header"
      style={{
        backgroundImage: "url('/images/he_banner.png')",
        backgroundSize: "cover",
        backgroundPosition: "center",
      }}
    >
      <div className="navbar">
        <div className="nav-left">
          <Link to={AppRoutePath.Home} className="brand-link">
            <img src="/images/he_logo.jpg" alt="Logo" className="brand-logo" />
          </Link>
          {isLoading ? (
            <div className="loading-container">
              <Spinner animation="grow" variant="primary" />
            </div>
          ) : (
            <>
              {isAuthenticated && user ? (
                <button className="btn-signin" onClick={handleLogout}>
                  {t("logout")}
                </button>
              ) : (
                <>
                  <button className="btn-signin" onClick={handleLogin}>
                    {t("login")}
                  </button>
                  <button className="btn-signin" onClick={handleRegister}>
                    {t("register")}
                  </button>
                </>
              )}
            </>
          )}
        </div>

        <div className="nav-right">
          <ul className="nav-list desktop-nav">
            <li>
              <Link to={AppRoutePath.Home}>{t("homes")}</Link>
            </li>
            <li>
              <Link to={AppRoutePath.AllServicesPage}>{t("services")}</Link>
            </li>
            <li>
              <Link to={AppRoutePath.AllGalleriesPage}>{t("gallery")}</Link>
            </li>
            {isAuthenticated && isAdmin && (
              <li>
                <Link to={AppRoutePath.DashboardPage}>{t("dashboard")}</Link>
              </li>
            )}
            {isAuthenticated && (
              <li>
                <Link to={AppRoutePath.Profile}>{t("profile")}</Link>
              </li>
            )}
          </ul>

          <button className="hamburger-btn" onClick={toggleMenu}>
            <span className="bar"></span>
            <span className="bar"></span>
            <span className="bar"></span>
          </button>

          {/* Language Toggle */}
          <div className="language-toggle">
            <button
              className="language-btn"
              onClick={() => handleLanguageChange("en")}
            >
              EN
            </button>
            <button
              className="language-btn"
              onClick={() => handleLanguageChange("fr")}
            >
              FR
            </button>
          </div>
        </div>
      </div>

      <nav className={`nav-links ${menuOpen ? "open" : ""}`}>
        <button className="close-btn" onClick={toggleMenu}>
          &times;
        </button>
        <ul>
          <li>
            <Link to={AppRoutePath.Home} onClick={toggleMenu}>
              {t("homes")}
            </Link>
          </li>
          <li>
            <Link to={AppRoutePath.AllServicesPage} onClick={toggleMenu}>
              {t("services")}
            </Link>
          </li>
          <li>
            <Link to={AppRoutePath.AllGalleriesPage} onClick={toggleMenu}>
              {t("gallery")}
            </Link>
          </li>
          {isAuthenticated && isAdmin && (
            <li>
              <Link to={AppRoutePath.DashboardPage} onClick={toggleMenu}>
                {t("dashboard")}
              </Link>
            </li>
          )}
          {isAuthenticated && (
            <li>
              <Link to={AppRoutePath.Profile} onClick={toggleMenu}>
                {t("profile")}
              </Link>
            </li>
          )}
        </ul>
      </nav>
    </header>
  );
}
