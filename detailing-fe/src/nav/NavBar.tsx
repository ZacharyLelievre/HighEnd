import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AppRoutePath } from "../routes/path.routes";
import { Spinner } from "react-bootstrap";
import { useAuth0 } from "@auth0/auth0-react";
import "./NavBar.css";

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

  // Toggles the off-canvas menu on small screens
  const toggleMenu = () => setMenuOpen(!menuOpen);

  useEffect(() => {
    if (!isAuthenticated) return;

    const checkIfUserExists = async () => {
      try {
        const accessToken = await getAccessTokenSilently();
        const response = await fetch("/api/customers/me", {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });

        if (response.status === 404) {
          console.log("User does not exist, redirecting to /onboarding");
          navigate(AppRoutePath.Onboarding);
        } else {
          const data = await response.json();
          console.log("Customer Data:", data);
        }
      } catch (error) {
        console.error("Error checking user existence:", error);
      }
    };

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

    checkIfUserExists();
    checkIfUserIsAdmin();
  }, [isAuthenticated, getAccessTokenSilently, navigate]);

  // Handlers
  const handleLogin = () => loginWithRedirect();
  const handleRegister = () => navigate(AppRoutePath.Onboarding);
  const handleLogout = () => {
    logout({ logoutParams: { returnTo: window.location.origin } });
  };

  return (
    <header
      className="custom-nav-header"
      style={{
        // The banner image
        backgroundImage: "url('/images/he_banner.png')",
        backgroundSize: "cover",
        backgroundPosition: "center",
      }}
    >
      <div className="navbar">
        {/* Left side: Logo + Auth Buttons (Login/Register or Logout) */}
        <div className="nav-left">
          <Link to={AppRoutePath.Home} className="brand-link">
            <img src="/images/he_logo.jpg" alt="Logo" className="brand-logo" />
          </Link>

          {/* If user is loading => show spinner */}
          {isLoading ? (
            <div className="loading-container">
              <Spinner animation="grow" variant="primary" />
            </div>
          ) : (
            <>
              {/* If authenticated => show "Log Out" */}
              {isAuthenticated && user ? (
                <button className="btn-signin" onClick={handleLogout}>
                  Log Out
                </button>
              ) : (
                /* Otherwise => show Login & Register */
                <>
                  <button className="btn-signin" onClick={handleLogin}>
                    Login
                  </button>
                  <button className="btn-signin" onClick={handleRegister}>
                    Register
                  </button>
                </>
              )}
            </>
          )}
        </div>

        {/* Right side (desktop) => Nav links in a horizontal list */}
        <div className="nav-right">
          <ul className="nav-list desktop-nav">
            <li>
              <Link to={AppRoutePath.Home}>Homes</Link>
            </li>
            <li>
              <Link to={AppRoutePath.AllServicesPage}>Services</Link>
            </li>
            <li>
              <Link to={AppRoutePath.AllGalleriesPage}>Gallery</Link>
            </li>
            {isAuthenticated && isAdmin && (
              <li>
                <Link to={AppRoutePath.DashboardPage}>Dashboard</Link>
              </li>
            )}
            {isAuthenticated && (
              <li>
                <Link to={AppRoutePath.Profile}>Profile</Link>
              </li>
            )}
          </ul>

          {/* Hamburger for mobile screens */}
          <button className="hamburger-btn" onClick={toggleMenu}>
            <span className="bar"></span>
            <span className="bar"></span>
            <span className="bar"></span>
          </button>
        </div>
      </div>

      {/* Off-Canvas Nav for mobile */}
      <nav className={`nav-links ${menuOpen ? "open" : ""}`}>
        {/* "X" button to close the off-canvas */}
        <button className="close-btn" onClick={toggleMenu}>
          &times;
        </button>

        <ul>
          <li>
            <Link to={AppRoutePath.Home} onClick={toggleMenu}>
              Homes
            </Link>
          </li>
          <li>
            <Link to={AppRoutePath.AllServicesPage} onClick={toggleMenu}>
              Services
            </Link>
          </li>
          <li>
            <Link to={AppRoutePath.AllGalleriesPage} onClick={toggleMenu}>
              Gallery
            </Link>
          </li>
          {isAuthenticated && isAdmin && (
            <li>
              <Link to={AppRoutePath.DashboardPage} onClick={toggleMenu}>
                Dashboard
              </Link>
            </li>
          )}
          {isAuthenticated && (
            <li>
              <Link to={AppRoutePath.Profile} onClick={toggleMenu}>
                Profile
              </Link>
            </li>
          )}
        </ul>
      </nav>
    </header>
  );
}
