import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useAuth0 } from "@auth0/auth0-react";
import { ServiceModel } from "./dtos/ServiceModel";
import { AppointmentModel } from "./dtos/AppointmentModel";
import { getAppointmentsByDate } from "../apis/getAppointmentsByDate";
import "./ServiceDetails.css";

interface Employee {
  employeeId: string;
  availability: {
    dayOfWeek: string;
    startTime: string;
    endTime: string;
  }[];
}

interface CustomerResponseModel {
  customerId: string;
  customerFirstName: string;
  customerLastName: string;
}

export default function ServiceDetail(): JSX.Element {
  const imageBaseUrl = process.env.REACT_APP_IMAGE_BASE_URL;
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;
  const { serviceId } = useParams<{ serviceId: string }>();
  const { getAccessTokenSilently } = useAuth0();

  const [service, setService] = useState<ServiceModel | null>(null);
  const [appointmentsThatDay, setAppointmentsThatDay] = useState<
    AppointmentModel[]
  >([]);
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [appointmentDate, setAppointmentDate] = useState<Date | null>(
    new Date(),
  );
  const [appointmentTime, setAppointmentTime] = useState<string>("");

  const availableTimeSlots = [
    "09:00 AM",
    "09:30 AM",
    "10:00 AM",
    "10:30 AM",
    "11:00 AM",
    "11:30 AM",
    "12:00 PM",
    "12:30 PM",
    "01:00 PM",
    "01:30 PM",
    "02:00 PM",
    "02:30 PM",
    "03:00 PM",
    "03:30 PM",
    "04:00 PM",
    "04:30 PM",
    "05:00 PM",
  ];

  // Helper: if imagePath is already a full URL (for added services) return it;
  // otherwise, assume it's an initial image and prefix with imageBaseUrl.
  const getImageUrl = (imagePath: string): string => {
    if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
      return imagePath;
    }
    const path = imagePath.startsWith("/") ? imagePath : `/${imagePath}`;
    return `${imageBaseUrl}${path}`;
  };

  useEffect(() => {
    const fetchService = async () => {
      try {
        const response = await axios.get(`${apiBaseUrl}/services/${serviceId}`);
        setService(response.data);
      } catch (error) {
        console.error("Error fetching service details:", error);
      }
    };
    fetchService();
  }, [serviceId, apiBaseUrl]);

  useEffect(() => {
    if (!appointmentDate) return;
    const dateStr = appointmentDate.toISOString().split("T")[0];
    getAppointmentsByDate(dateStr)
      .then((res) => setAppointmentsThatDay(res))
      .catch((err) => console.error("Error fetching day appointments", err));
  }, [appointmentDate]);

  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        const resp = await axios.get<Employee[]>(`${apiBaseUrl}/employees`);
        setEmployees(resp.data);
      } catch (err) {
        console.error("Error fetching employees:", err);
      }
    };
    fetchEmployees();
  }, [apiBaseUrl]);

  const parseDurationInMinutes = (timeStr: string): number => {
    const trimmed = timeStr.trim().toLowerCase();
    if (trimmed.includes("minute")) {
      return Math.round(parseFloat(trimmed));
    } else if (trimmed.includes("hour")) {
      return Math.round(parseFloat(trimmed) * 60);
    }
    return 60;
  };

  const convertTo24h = (time12h: string): string => {
    const [timePart, meridian] = time12h.split(" ");
    let [hour, min] = timePart.split(":");
    let hourNum = parseInt(hour, 10);
    const minuteNum = parseInt(min, 10) || 0;
    if (meridian.toUpperCase() === "PM" && hourNum < 12) hourNum += 12;
    if (meridian.toUpperCase() === "AM" && hourNum === 12) hourNum = 0;
    return `${hourNum.toString().padStart(2, "0")}:${minuteNum.toString().padStart(2, "0")}`;
  };

  const parseDbTimeToMinutes = (dbTime: string): number => {
    const parts = dbTime.split(":").map(Number);
    const hour = parts[0] || 0;
    const minute = parts[1] || 0;
    return hour * 60 + minute;
  };

  const hasAtLeastOneAvailableEmployee = (
    slotStart: number,
    slotEnd: number,
    date: Date,
  ): boolean => {
    if (!employees.length) return false;
    const dayOfWeek = date
      .toLocaleDateString("en-CA", { weekday: "long" })
      .toUpperCase();
    for (const emp of employees) {
      const isEmployeeAvailable = emp.availability?.some((a) => {
        if (a.dayOfWeek.toUpperCase() !== dayOfWeek) return false;
        const [startH, startM] = a.startTime.split(":").map(Number);
        const [endH, endM] = a.endTime.split(":").map(Number);
        const empAvailStart = startH * 60 + startM;
        const empAvailEnd = endH * 60 + endM;
        return empAvailStart <= slotStart && empAvailEnd >= slotEnd;
      });
      if (isEmployeeAvailable) return true;
    }
    return false;
  };

  const isSlotConflicting = (slot: string): boolean => {
    if (!service || !appointmentDate) return false;
    const [hrStr] = slot.split(":");
    let hourNum = parseInt(hrStr, 10);
    const isPM = slot.includes("PM");
    if (isPM && hourNum < 12) hourNum += 12;
    if (!isPM && hourNum === 12) hourNum = 0;
    const minuteNum = slot.includes("30") ? 30 : 0;
    const slotStart = hourNum * 60 + minuteNum;
    const slotEnd = slotStart + 30;
    if (!hasAtLeastOneAvailableEmployee(slotStart, slotEnd, appointmentDate)) {
      return true;
    }
    for (const appt of appointmentsThatDay) {
      const existingStart = parseDbTimeToMinutes(appt.appointmentTime);
      const existingEnd = parseDbTimeToMinutes(appt.appointmentEndTime);
      const blockedStart = Math.max(existingStart - 30, 0);
      const blockedEnd = existingEnd;
      if (slotStart < blockedEnd && slotEnd > blockedStart) {
        return true;
      }
    }
    return false;
  };

  const handleAppointmentSubmit = async (
    e: React.FormEvent<HTMLFormElement>,
  ) => {
    e.preventDefault();
    if (!appointmentDate || !appointmentTime) {
      alert("Please select a date & time first!");
      return;
    }
    const durationInMinutes = parseDurationInMinutes(
      service?.timeRequired || "60",
    );
    const start24h = convertTo24h(appointmentTime);
    const [startHStr, startMStr] = start24h.split(":");
    const startH = parseInt(startHStr, 10);
    const startM = parseInt(startMStr, 10);
    const endTimeDate = new Date(2022, 0, 1, startH, startM);
    endTimeDate.setMinutes(endTimeDate.getMinutes() + durationInMinutes);
    const finalEndH = endTimeDate.getHours().toString().padStart(2, "0");
    const finalEndM = endTimeDate.getMinutes().toString().padStart(2, "0");
    const finalEndTime = `${finalEndH}:${finalEndM}`;

    try {
      const token = await getAccessTokenSilently();
      const meResp = await axios.get<CustomerResponseModel>(
        `${apiBaseUrl}/customers/me`,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      if (!meResp.data) {
        alert("Could not fetch your customer profile!");
        return;
      }
      const { customerId, customerFirstName, customerLastName } = meResp.data;
      const dateStr = appointmentDate.toISOString().split("T")[0];
      const finalStartTime = `${startH.toString().padStart(2, "0")}:${startM.toString().padStart(2, "0")}`;

      const requestBody = {
        appointmentDate: dateStr,
        appointmentTime: finalStartTime,
        appointmentEndTime: finalEndTime,
        serviceId: service?.serviceId,
        serviceName: service?.serviceName,
        customerId,
        customerName: `${customerFirstName} ${customerLastName}`,
        employeeId: "UNASSIGNED",
        employeeName: "UNASSIGNED",
      };

      const postResp = await axios.post(
        `${apiBaseUrl}/appointments`,
        requestBody,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      console.log("Appointment created:", postResp.data);

      const newApptId = postResp.data.appointmentId;
      const getResp = await axios.get<AppointmentModel>(
        `${apiBaseUrl}/appointments/${newApptId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      setAppointmentsThatDay((prev) => [...prev, getResp.data]);

      alert("Appointment scheduled successfully!");
    } catch (err) {
      console.error("Error creating appointment:", err);
      alert("Something went wrong scheduling the appointment!");
    }
  };

  if (!service) {
    return <div>Loading Service Info ...</div>;
  }

  return (
    <div className="service-details-container">
      <div className="service-details-top">
        <div className="service-image-wrapper">
          <img
            className="service-image"
            src={getImageUrl(service.imagePath)}
            alt={service.serviceName}
          />
        </div>
        <div className="service-info">
          <h1>{service.serviceName}</h1>
          <h2>${service.price.toFixed(2)}</h2>
          <ul className="service-description">
            <li>High-quality service with attention to detail</li>
            <li>Experienced and trained professionals</li>
            <li>Use of premium products and tools</li>
            <li>Customer satisfaction guaranteed</li>
            <li>Flexible appointment scheduling</li>
          </ul>
        </div>
      </div>
      <hr className="separator" />
      <div className="service-scheduler">
        <h2 className="scheduler-title">Schedule Your Appointment</h2>
        <form onSubmit={handleAppointmentSubmit} className="scheduler-form">
          <div className="scheduler-columns">
            <div className="datetime-section left">
              <label>Select Date:</label>
              <div className="calendar-container">
                <DatePicker
                  inline
                  selected={appointmentDate}
                  onChange={(date) => date && setAppointmentDate(date)}
                  minDate={new Date()}
                />
              </div>
            </div>
            <div className="vertical-separator" />
            <div className="datetime-section right">
              <label>Select Time:</label>
              <div className="time-slots-container">
                {availableTimeSlots.map((slot, index) => {
                  const conflict = isSlotConflicting(slot);
                  const slotClass = conflict
                    ? "time-slot red"
                    : "time-slot green";
                  const isSelected = appointmentTime === slot;
                  return (
                    <button
                      type="button"
                      key={index}
                      onClick={() => {
                        if (!conflict) {
                          setAppointmentTime(slot);
                        }
                      }}
                      className={`${slotClass} ${isSelected ? "selected" : ""}`}
                      disabled={conflict}
                    >
                      {slot}
                    </button>
                  );
                })}
              </div>
            </div>
          </div>
          <button type="submit" className="schedule-button">
            Schedule Appointment
          </button>
        </form>
      </div>
    </div>
  );
}
