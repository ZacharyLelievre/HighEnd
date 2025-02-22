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
  const [isMobile, setIsMobile] = useState(window.innerWidth < 991);
  const navigate = useNavigate();
  const { t, i18n } = useTranslation();

  const handleLanguageChange = (lang: string) => {
    i18n.changeLanguage(lang);
  };

  const toggleMenu = () => setMenuOpen(!menuOpen);

  useEffect(() => {
    const handleResize = () => setIsMobile(window.innerWidth < 991);
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

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
          {/* Always show logo on left */}
          <div className="nav-left">
            <Link to={AppRoutePath.Home} className="brand-link">
              <img src="/images/he_logo.jpg" alt="Logo" className="brand-logo" />
            </Link>
            {/* On desktop, show auth buttons */}
            {!isMobile && !isLoading && (
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
            {/* Desktop nav links */}
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
              <li>
                <Link to="/about">{t("about_link")}</Link>
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

            {/* Hamburger button visible on mobile */}
            {isMobile && (
                <button
                    className="hamburger-btn"
                    onClick={toggleMenu}
                    aria-label={t("menu")}
                >
                  <span className="bar"></span>
                  <span className="bar"></span>
                  <span className="bar"></span>
                </button>
            )}

            {/* Desktop language toggles */}
            {!isMobile && (
                <div className="language-toggle">
                  <button
                      className={`language-btn ${
                          i18n.language === "en" ? "active" : ""
                      }`}
                      onClick={() => handleLanguageChange("en")}
                  >
                    EN
                  </button>
                  <button
                      className={`language-btn ${
                          i18n.language === "fr" ? "active" : ""
                      }`}
                      onClick={() => handleLanguageChange("fr")}
                  >
                    FR
                  </button>
                </div>
            )}
          </div>
        </div>

        {/* Mobile menu */}
        {isMobile && (
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
                <li>
                  <Link to="/about" onClick={toggleMenu}>
                    {t("about_link")}
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
                {/* Mobile-only auth buttons */}
                {!isLoading && (
                    <>
                      {!isAuthenticated ? (
                          <>
                            <li>
                              <button
                                  className="btn-signin"
                                  onClick={() => {
                                    toggleMenu();
                                    handleLogin();
                                  }}
                              >
                                {t("login")}
                              </button>
                            </li>
                            <li>
                              <button
                                  className="btn-signin"
                                  onClick={() => {
                                    toggleMenu();
                                    handleRegister();
                                  }}
                              >
                                {t("register")}
                              </button>
                            </li>
                          </>
                      ) : (
                          <li>
                            <button
                                className="btn-signin"
                                onClick={() => {
                                  toggleMenu();
                                  handleLogout();
                                }}
                            >
                              {t("logout")}
                            </button>
                          </li>
                      )}
                    </>
                )}
                {/* Mobile-only language toggles */}
                <li>
                  <div className="language-toggle">
                    <button
                        className={`language-btn ${
                            i18n.language === "en" ? "active" : ""
                        }`}
                        onClick={() => {
                          toggleMenu();
                          handleLanguageChange("en");
                        }}
                    >
                      EN
                    </button>
                    <button
                        className={`language-btn ${
                            i18n.language === "fr" ? "active" : ""
                        }`}
                        onClick={() => {
                          toggleMenu();
                          handleLanguageChange("fr");
                        }}
                    >
                      FR
                    </button>
                  </div>
                </li>
              </ul>
            </nav>
        )}
      </header>
  );
}