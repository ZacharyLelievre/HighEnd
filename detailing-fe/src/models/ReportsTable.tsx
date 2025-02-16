import React, { useEffect, useState, useRef } from "react";
import axios from "axios";
import { useAuth0 } from "@auth0/auth0-react";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";
import { AppRoutePath } from "../routes/path.routes";
import { useNavigate } from "react-router-dom";

interface Appointment {
  appointmentId: string;
  serviceId: string;
  serviceName: string;
}

interface Service {
  serviceId: string;
  serviceName: string;
  price: number;
}

interface ReportRow {
  serviceName: string;
  bookingsCount: number;
  revenue: number;
  statusColor: "RED" | "YELLOW" | "GREEN" | string;
}

export default function ReportsTable() {
  const navigate = useNavigate();
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;
  const { getAccessTokenSilently, isAuthenticated } = useAuth0();
  const [reports, setReports] = useState<ReportRow[]>([]);
  const [loading, setLoading] = useState(true);

  // Ref for the container wrapping the table (used for PDF generation)
  const containerRef = useRef<HTMLDivElement>(null);

  const getAppointmentsUrl = `${apiBaseUrl}/appointments`;
  const getServicesUrl = `${apiBaseUrl}/services`;

  useEffect(() => {
    async function loadReportData() {
      if (!isAuthenticated) {
        console.log("User is not authenticated; skipping aggregator fetch.");
        setLoading(false);
        return;
      }
      try {
        console.log("[ReportsTable] Loading data for front-end aggregator...");
        const token = await getAccessTokenSilently();
        console.log(
          "[ReportsTable] Got token from Auth0:",
          token.slice(0, 20),
          "...",
        );
        const [appointmentsRes, servicesRes] = await Promise.all([
          axios.get<Appointment[]>(getAppointmentsUrl, {
            headers: { Authorization: `Bearer ${token}` },
          }),
          axios.get<Service[]>(getServicesUrl, {
            headers: { Authorization: `Bearer ${token}` },
          }),
        ]);

        const allAppointments = appointmentsRes.data;
        const allServices = servicesRes.data;

        console.log(
          "[ReportsTable] All appointments fetched:",
          allAppointments,
        );
        console.log("[ReportsTable] All services fetched:", allServices);

        const rawList = allServices.map((service) => {
          const bookingCount = allAppointments.filter(
            (appt) => appt.serviceId === service.serviceId,
          ).length;
          const revenue = bookingCount * service.price;
          return {
            serviceName: service.serviceName,
            bookingsCount: bookingCount,
            revenue,
            statusColor: "PENDING",
          };
        });

        console.log(
          "[ReportsTable] Aggregation (before color coding):",
          rawList,
        );

        if (rawList.length > 0) {
          const minRevenue = Math.min(...rawList.map((r) => r.revenue));
          const maxRevenue = Math.max(...rawList.map((r) => r.revenue));

          rawList.forEach((r) => {
            if (r.revenue === minRevenue && minRevenue < maxRevenue) {
              r.statusColor = "RED";
            } else if (r.revenue === maxRevenue && maxRevenue > minRevenue) {
              r.statusColor = "GREEN";
            } else {
              r.statusColor = "YELLOW";
            }
          });
        }
        setReports(rawList);
      } catch (error) {
        console.error(
          "[ReportsTable] Error building front-end aggregator:",
          error,
        );
      } finally {
        setLoading(false);
      }
    }
    loadReportData();
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
      pdf.save("reports.pdf");
    }
  };

  const handleAddPromotion = () => {
    navigate(AppRoutePath.Promotions);
  };

  const getStatusCard = (status: string) => {
    let backgroundColor = "";
    let text = "";
    let textColor = "#fff";

    switch (status) {
      case "RED":
        backgroundColor = "#ff4d4d";
        text = "Doing Bad";
        break;
      case "GREEN":
        backgroundColor = "#4dff4d";
        text = "Doing Great";
        break;
      case "YELLOW":
        backgroundColor = "#ffeb3b";
        text = "Doing Okay";
        textColor = "#333";
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
    return <div>Loading Reports (Front-End Aggregator)...</div>;
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
            <th style={{ padding: "12px", border: "1px solid #ddd" }}>
              Service
            </th>
            <th style={{ padding: "12px", border: "1px solid #ddd" }}>
              Bookings
            </th>
            <th style={{ padding: "12px", border: "1px solid #ddd" }}>
              Revenue
            </th>
            <th style={{ padding: "12px", border: "1px solid #ddd" }}>
              Status
            </th>
          </tr>
        </thead>
        <tbody>
          {reports.map((r, idx) => (
            <tr key={idx}>
              <td style={{ padding: "12px", border: "1px solid #ddd" }}>
                {r.serviceName}
              </td>
              <td
                style={{
                  padding: "12px",
                  border: "1px solid #ddd",
                  textAlign: "center",
                }}
              >
                {r.bookingsCount}
              </td>
              <td
                style={{
                  padding: "12px",
                  border: "1px solid #ddd",
                  textAlign: "center",
                }}
              >
                ${r.revenue.toFixed(2)}
              </td>
              <td
                style={{
                  padding: "12px",
                  border: "1px solid #ddd",
                  textAlign: "center",
                }}
              >
                {getStatusCard(r.statusColor)}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div
        style={{
          position: "absolute",
          bottom: "10px",
          right: "10px",
          display: "flex",
          gap: "10px",
        }}
      >
        <button
          onClick={generatePDF}
          style={{
            padding: "4px 8px",
            fontSize: "16px",
            backgroundColor: "black",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          Download PDF
        </button>

        <button
          onClick={handleAddPromotion}
          style={{
            padding: "4px 8px",
            fontSize: "16px",
            backgroundColor: "#333",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          Add Promotion
        </button>
      </div>
    </div>
  );
}
