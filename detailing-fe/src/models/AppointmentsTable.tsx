import React, { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useAuth0 } from "@auth0/auth0-react";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";

interface Appointment {
  appointmentId: string;
  appointmentDate: string;
  appointmentTime: string;
  serviceId: string;
  serviceName: string;
  customerName: string;
  employeeName: string;
  status:
    | "CONFIRMED"
    | "REJECTED"
    | "CANCELLED"
    | "PENDING"
    | "PROGRESS"
    | "COMPLETED"
    | string;
}

interface Service {
  serviceId: string;
  serviceName: string;
  price: number;
}

export default function AppointmentsTable() {
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;
  const { getAccessTokenSilently, isAuthenticated } = useAuth0();
  const [appointments, setAppointments] = useState<Appointment[]>([]);
  const [services, setServices] = useState<Service[]>([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  // Ref for PDF generation
  const containerRef = useRef<HTMLDivElement>(null);

  const getAppointmentsUrl = `${apiBaseUrl}/appointments`;
  const getServicesUrl = `${apiBaseUrl}/services`;

  useEffect(() => {
    async function loadData() {
      if (!isAuthenticated) {
        console.log("User not authenticated, skipping fetch.");
        setLoading(false);
        return;
      }
      try {
        const token = await getAccessTokenSilently();
        const [appointmentsRes, servicesRes] = await Promise.all([
          axios.get<Appointment[]>(getAppointmentsUrl, {
            headers: { Authorization: `Bearer ${token}` },
          }),
          axios.get<Service[]>(getServicesUrl, {
            headers: { Authorization: `Bearer ${token}` },
          }),
        ]);
        setAppointments(appointmentsRes.data);
        setServices(servicesRes.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      } finally {
        setLoading(false);
      }
    }
    loadData();
  }, [getAccessTokenSilently, isAuthenticated]);

  const generatePDF = async () => {
    if (containerRef.current) {
      const canvas = await html2canvas(containerRef.current);
      const imgData = canvas.toDataURL("image/png");
      const pdf = new jsPDF("p", "mm", "a4");
      const pdfWidth = pdf.internal.pageSize.getWidth();
      const imgProps = pdf.getImageProperties(imgData);
      const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;
      pdf.addImage(imgData, "PNG", 0, 0, pdfWidth, pdfHeight);
      pdf.save("appointments.pdf");
    }
  };

  const getStatusCard = (status: string) => {
    let backgroundColor = "";
    let text = "";
    let textColor = "#fff";

    switch (status) {
      case "COMPLETED":
        backgroundColor = "#4dff4d";
        text = "Completed";
        break;
      case "CANCELLED":
        backgroundColor = "#ff4d4d";
        text = "Cancelled";
        break;
      case "PROGRESS":
        backgroundColor = "#ffeb3b";
        text = "In Progress";
        textColor = "#333";
        break;
      case "CONFIRMED":
        backgroundColor = "#4d79ff";
        text = "Confirmed";
        break;
      case "PENDING":
        backgroundColor = "#cccccc";
        text = "Pending";
        textColor = "#333";
        break;
      case "REJECTED":
        backgroundColor = "#cc0000";
        text = "Rejected";
        break;
      default:
        backgroundColor = "#ccc";
        text = status;
        textColor = "#333";
        break;
    }

    return (
      <div
        style={{
          backgroundColor,
          color: textColor,
          borderRadius: "4px",
          padding: "4px 8px",
          display: "inline-block",
          fontWeight: "bold",
        }}
      >
        {text}
      </div>
    );
  };

  if (loading) {
    return <div>Loading Appointments...</div>;
  }

  return (
    <div
      ref={containerRef}
      style={{ position: "relative", paddingBottom: "50px" }}
    >
      <table
        style={{
          marginTop: "20px",
          borderCollapse: "collapse",
          width: "100%",
          boxShadow: "0 2px 5px rgba(0,0,0,0.1)",
        }}
      >
        <thead>
          <tr style={{ backgroundColor: "#f2f2f2" }}>
            <th style={{ padding: "12px", border: "1px solid #ddd" }}>Date</th>
            <th style={{ padding: "12px", border: "1px solid #ddd" }}>Time</th>
            <th style={{ padding: "12px", border: "1px solid #ddd" }}>
              Customer
            </th>
            <th style={{ padding: "12px", border: "1px solid #ddd" }}>
              Service
            </th>
            <th style={{ padding: "12px", border: "1px solid #ddd" }}>
              Employee
            </th>
            <th style={{ padding: "12px", border: "1px solid #ddd" }}>
              Amount
            </th>
            <th style={{ padding: "12px", border: "1px solid #ddd" }}>
              Status
            </th>
          </tr>
        </thead>
        <tbody>
          {appointments.map((appt) => {
            const service = services.find(
              (s) => s.serviceId === appt.serviceId,
            );
            const amountDisplay = service ? `$${service.price.toFixed(2)}` : "";
            return (
              <tr
                key={appt.appointmentId}
                onClick={() =>
                  navigate(`/my-appointments/${appt.appointmentId}`)
                }
                style={{ cursor: "pointer" }}
              >
                <td
                  style={{
                    padding: "12px",
                    border: "1px solid #ddd",
                    textAlign: "center",
                  }}
                >
                  {appt.appointmentDate}
                </td>
                <td
                  style={{
                    padding: "12px",
                    border: "1px solid #ddd",
                    textAlign: "center",
                  }}
                >
                  {appt.appointmentTime}
                </td>
                <td
                  style={{
                    padding: "12px",
                    border: "1px solid #ddd",
                    textAlign: "center",
                  }}
                >
                  {appt.customerName}
                </td>
                <td
                  style={{
                    padding: "12px",
                    border: "1px solid #ddd",
                    textAlign: "center",
                  }}
                >
                  {appt.serviceName}
                </td>
                <td
                  style={{
                    padding: "12px",
                    border: "1px solid #ddd",
                    textAlign: "center",
                  }}
                >
                  {appt.employeeName}
                </td>
                <td
                  style={{
                    padding: "12px",
                    border: "1px solid #ddd",
                    textAlign: "center",
                  }}
                >
                  {amountDisplay}
                </td>
                <td
                  style={{
                    padding: "12px",
                    border: "1px solid #ddd",
                    textAlign: "center",
                  }}
                >
                  {getStatusCard(appt.status)}
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
      <button
        onClick={generatePDF}
        style={{
          position: "absolute",
          bottom: "10px",
          right: "10px",
          padding: "4px 8px",
          fontSize: "16px",
          backgroundColor: "black",
          color: "white",
          border: "none",
          borderRadius: "4px",
          cursor: "pointer",
          width: "16%",
        }}
      >
        Download PDF
      </button>
    </div>
  );
}
