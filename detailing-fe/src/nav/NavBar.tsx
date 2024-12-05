import { Link } from "react-router-dom";
import { AppRoutePath } from "../routes/path.routes";
import { Navbar, Container, Nav } from "react-bootstrap"; // Correct import
import "./NavBar.css"; // Import CSS file for styling

export function NavBar(): JSX.Element {
  return (
    <Navbar
      style={{
        backgroundImage: "url('/images/he_banner.png')", // Path to your background image
        backgroundSize: "cover",
        backgroundPosition: "center",
      }}
      expand="lg"
    >
      <Container fluid>
        {/* Logo on the left */}
        <Navbar.Brand as={Link} to={AppRoutePath.Home}>
          <img
            src="/images/he_logo.jpg" // Path to your logo image
            alt="Logo"
            style={{ height: "70px", marginRight: "15px" }} // Adjust height for larger logo
          />
        </Navbar.Brand>

        {/* Navbar Toggle for responsiveness */}
        <Navbar.Toggle aria-controls="basic-navbar-nav" />

        {/* Links aligned to the right */}
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto"> {/* Use ms-auto for right alignment */}
            <Nav.Link
              as={Link}
              to={AppRoutePath.Home}
              className="text-white nav-item-spacing"
            >
              Home
            </Nav.Link>
            <Nav.Link
              as={Link}
              to={AppRoutePath.AllServicesPage}
              className="text-white nav-item-spacing"
            >
              Services
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}
