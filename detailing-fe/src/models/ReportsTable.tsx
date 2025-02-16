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
    const containerRef = useRef<HTMLDivElement>(null);

    // New Service Form State
    const [showAddService, setShowAddService] = useState(false);
    const [serviceName, setServiceName] = useState("");
    const [timeRequired, setTimeRequired] = useState("");
    const [price, setPrice] = useState<number>(0);
    const [file, setFile] = useState<File | null>(null);

    // URLs
    const getAppointmentsUrl = `${apiBaseUrl}/appointments`;
    const getServicesUrl = `${apiBaseUrl}/services`;

    // Load report data
    useEffect(() => {
        async function loadReportData() {
            if (!isAuthenticated) {
                console.log("User is not authenticated; skipping aggregator fetch.");
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

                const allAppointments = appointmentsRes.data;
                const allServices = servicesRes.data;

                const rawList = allServices.map((service) => {
                    const bookingCount = allAppointments.filter(
                        (appt) => appt.serviceId === service.serviceId
                    ).length;
                    const revenue = bookingCount * service.price;
                    return {
                        serviceName: service.serviceName,
                        bookingsCount: bookingCount,
                        revenue,
                        statusColor: "PENDING",
                    };
                });

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
                console.error("Error building front-end aggregator:", error);
            } finally {
                setLoading(false);
            }
        }
        loadReportData();
    }, [getAccessTokenSilently, isAuthenticated, getAppointmentsUrl, getServicesUrl]);

    // PDF generation
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

    // Handle file change
    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files.length > 0) {
            setFile(e.target.files[0]);
        }
    };

    // Add Service form submit
    const handleAddServiceSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!file) {
            alert("Please select a file.");
            return;
        }
        try {
            const formData = new FormData();
            formData.append("serviceName", serviceName);
            formData.append("timeRequired", timeRequired);
            formData.append("price", price.toString());
            formData.append("image", file);

            const token = await getAccessTokenSilently();
            const response = await axios.post(`${apiBaseUrl}/services`, formData, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "multipart/form-data",
                },
            });

            console.log("New service created:", response.data);
            alert("Service created successfully!");

            // Clear form and hide modal
            setServiceName("");
            setTimeRequired("");
            setPrice(0);
            setFile(null);
            setShowAddService(false);

            // Optionally, refresh the table data if needed
        } catch (err) {
            console.error("Error creating service:", err);
            alert("Error creating service.");
        }
    };

    const handleAddPromotion = () => {
        // Navigate to the promotions route; adjust as needed.
        navigate(AppRoutePath.Promotions || "/promotions");
    };

    // Helper for status card
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

    // Common button style
    const buttonStyle = {
        padding: "8px 16px",
        fontSize: "16px",
        backgroundColor: "black",
        color: "white",
        border: "none",
        borderRadius: "4px",
        cursor: "pointer",
        minWidth: "150px",
        textAlign: "center" as const,
    };

    return (
        <div ref={containerRef} style={{ position: "relative", paddingBottom: "50px" }}>
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
                    <th style={{ padding: "12px", border: "1px solid #ddd" }}>Service</th>
                    <th style={{ padding: "12px", border: "1px solid #ddd" }}>Bookings</th>
                    <th style={{ padding: "12px", border: "1px solid #ddd" }}>Revenue</th>
                    <th style={{ padding: "12px", border: "1px solid #ddd" }}>Status</th>
                </tr>
                </thead>
                <tbody>
                {reports.map((r, idx) => (
                    <tr key={idx}>
                        <td style={{ padding: "12px", border: "1px solid #ddd" }}>{r.serviceName}</td>
                        <td style={{ padding: "12px", border: "1px solid #ddd", textAlign: "center" }}>
                            {r.bookingsCount}
                        </td>
                        <td style={{ padding: "12px", border: "1px solid #ddd", textAlign: "center" }}>
                            ${r.revenue.toFixed(2)}
                        </td>
                        <td style={{ padding: "12px", border: "1px solid #ddd", textAlign: "center" }}>
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
                <button onClick={generatePDF} style={buttonStyle}>
                    Download PDF
                </button>
                <button onClick={() => setShowAddService(true)} style={buttonStyle}>
                    Add Service
                </button>
                <button onClick={handleAddPromotion} style={buttonStyle}>
                    Promotions
                </button>
            </div>

            {/* Add Service Modal */}
            {showAddService && (
                <div
                    style={{
                        position: "fixed",
                        top: 0,
                        left: 0,
                        width: "100%",
                        height: "100%",
                        backgroundColor: "rgba(0,0,0,0.5)",
                    }}
                >
                    <div
                        style={{
                            backgroundColor: "#fff",
                            padding: "20px",
                            maxWidth: "400px",
                            margin: "100px auto",
                            borderRadius: "8px",
                        }}
                    >
                        <h2>Add New Service</h2>
                        <form onSubmit={handleAddServiceSubmit}>
                            <div>
                                <label>Service Name:</label>
                                <input
                                    type="text"
                                    value={serviceName}
                                    onChange={(e) => setServiceName(e.target.value)}
                                    required
                                />
                            </div>
                            <div>
                                <label>Time Required:</label>
                                <input
                                    type="text"
                                    value={timeRequired}
                                    onChange={(e) => setTimeRequired(e.target.value)}
                                    required
                                />
                            </div>
                            <div>
                                <label>Price:</label>
                                <input
                                    type="number"
                                    step="0.01"
                                    value={price}
                                    onChange={(e) => setPrice(parseFloat(e.target.value))}
                                    required
                                />
                            </div>
                            <div>
                                <label>Image:</label>
                                <input type="file" onChange={handleFileChange} required />
                            </div>
                            <button type="submit">Save</button>
                            <button type="button" onClick={() => setShowAddService(false)}>
                                Cancel
                            </button>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}