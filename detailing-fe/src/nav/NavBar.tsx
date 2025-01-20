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
  const navigate = useNavigate();

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

        // If the customer is not found, 404 => redirect them to /onboarding
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

  const handleLogin = async () => {
    await loginWithRedirect();
  };

  // const handleRegister = () => {
  //   navigate(AppRoutePath.Onboarding);
  // };
  const handleRegister = () => {
    navigate(AppRoutePath.Onboarding);
  };

  const handleLogout = () => {
    const returnToUrl = `${window.location.origin}`;
    logout({
      logoutParams: { returnTo: returnToUrl },
    });
  };

  return (
    <Navbar
      style={{
        backgroundImage: "url('/images/he_banner.png')",
        backgroundSize: "cover",
        backgroundPosition: "center",
      }}
      expand="lg"
    >
      <Container fluid>
        <Navbar.Brand as={Link} to={AppRoutePath.Home}>
          <img
            src="/images/he_logo.jpg"
            alt="Logo"
            style={{ height: "70px", marginRight: "15px" }}
          />
        </Navbar.Brand>

        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto">
            <Nav.Link
              as={Link}
              to={AppRoutePath.Home}
              className="text-white nav-item-spacing"
            >
              Homes
            </Nav.Link>
            <Nav.Link
              as={Link}
              to={AppRoutePath.AllServicesPage}
              className="text-white nav-item-spacing"
            >
              Services
            </Nav.Link>
            <Nav.Link
              as={Link}
              to={AppRoutePath.AllGalleriesPage}
              className="text-white nav-item-spacing"
            >
              Gallery
            </Nav.Link>

            {/* If user is admin, show Dashboard */}
            {isAuthenticated && isAdmin && (
              <Nav.Link
                as={Link}
                to={AppRoutePath.DashboardPage}
                className="text-white nav-item-spacing"
              >
                Dashboard
              </Nav.Link>
            )}

            {isAuthenticated && (
              <Nav.Link
                as={Link}
                to={AppRoutePath.Profile}
                className="text-white nav-item-spacing"
              >
                Profile
              </Nav.Link>
            )}

            {isLoading ? (
              <div className="loading-container">
                <Spinner
                  className="spinner-grow"
                  animation="grow"
                  variant="primary"
                />
              </div>
            ) : isAuthenticated && user ? (
              <NavDropdown
                title={
                  <>
                    <img
                      src={user.picture}
                      alt={user.name}
                      className="user-avatar"
                    />{" "}
                  </>
                }
                id="user-dropdown"
                align="end"
              >
                <NavDropdown.Item onClick={handleLogout}>
                  Log out
                </NavDropdown.Item>
              </NavDropdown>
            ) : (
              <>
                <Button
                  onClick={handleLogin}
                  variant="outline-light"
                  className="btn-signin"
                >
                  Login
                </Button>
                <Button
                  onClick={handleRegister}
                  variant="outline-light"
                  className="btn-signin"
                >
                  Register
                </Button>
                {/*<Button*/}
                {/*  onClick={handleRegister}*/}
                {/*  variant="outline-light"*/}
                {/*  className="btn-signin"*/}
                {/*>*/}
                {/*  Register*/}
                {/*</Button>*/}
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}
