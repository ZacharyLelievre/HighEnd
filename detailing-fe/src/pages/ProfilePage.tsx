// src/models/ProfilePage.tsx
import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import axios from "axios";
import { NavBar } from "../nav/NavBar";
import "./ProfilePage.css";
import { CustomerModel } from "../models/dtos/CustomerModel";
import { EmployeeModel } from "../models/dtos/EmployeeModel";

type UserProfile = CustomerModel | EmployeeModel;

export function ProfilePage() {
  const { getAccessTokenSilently, user } = useAuth0();
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [userType, setUserType] = useState<"Customer" | "Employee" | null>(
    null,
  );
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        setLoading(true);
        const token = await getAccessTokenSilently();

        // Attempt to fetch Customer profile
        try {
          const customerResponse = await axios.get<CustomerModel>(
            "http://localhost:8080/api/customers/me",
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            },
          );
          setProfile(customerResponse.data);
          setUserType("Customer");
        } catch (customerError: any) {
          if (customerError.response && customerError.response.status === 404) {
            // If Customer not found, attempt to fetch Employee profile
            try {
              const employeeResponse = await axios.get<EmployeeModel>(
                "http://localhost:8080/api/employees/me",
                {
                  headers: {
                    Authorization: `Bearer ${token}`,
                  },
                },
              );
              setProfile(employeeResponse.data);
              setUserType("Employee");
            } catch (employeeError: any) {
              if (
                employeeError.response &&
                employeeError.response.status === 404
              ) {
                setError(
                  "No profile information found for the authenticated user.",
                );
              } else {
                setError("Error fetching employee profile.");
                console.error(
                  "Error fetching employee profile:",
                  employeeError,
                );
              }
            }
          } else {
            setError("Error fetching customer profile.");
            console.error("Error fetching customer profile:", customerError);
          }
        }
      } catch (err) {
        setError("An unexpected error occurred while fetching the profile.");
        console.error("Unexpected error:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, [getAccessTokenSilently]);

  // Loading State
  if (loading) {
    return (
      <div>
        <NavBar />
        <div className="profile-container">
          <p>Loading your profile...</p>
        </div>
      </div>
    );
  }

  // Error State
  if (error) {
    return (
      <div>
        <NavBar />
        <div className="profile-container">
          <p>{error}</p>
        </div>
      </div>
    );
  }

  // Profile Not Found
  if (!profile || !userType) {
    return (
      <div>
        <NavBar />
        <div className="profile-container">
          <p>No profile information available.</p>
        </div>
      </div>
    );
  }

  // Render Profile Based on User Type
  return (
    <div>
      <NavBar />
      <div className="profile-container">
        <div className="profile-card">
          <div className="profile-header">
            <div className="profile-avatar">
              <img
                src={
                  user?.picture ||
                  (userType === "Employee" &&
                  (profile as EmployeeModel).imagePath
                    ? `https://highend-zke6.onrender.com/${(profile as EmployeeModel).imagePath}`
                    : "https://via.placeholder.com/100")
                }
                alt={
                  userType === "Customer"
                    ? `${(profile as CustomerModel).customerFirstName} ${(profile as CustomerModel).customerLastName}`
                    : `${(profile as EmployeeModel).first_name} ${(profile as EmployeeModel).last_name}`
                }
              />
            </div>
            <div className="profile-name">
              <h2>
                {userType === "Customer"
                  ? `${(profile as CustomerModel).customerFirstName} ${(profile as CustomerModel).customerLastName}`
                  : `${(profile as EmployeeModel).first_name} ${(profile as EmployeeModel).last_name}`}
              </h2>
              <p className="profile-email">
                {userType === "Customer"
                  ? (profile as CustomerModel).customerEmailAddress
                  : (profile as EmployeeModel).email}
              </p>
            </div>
          </div>
          <div className="profile-details">
            {userType === "Customer" && (
              <>
                <div className="detail-row">
                  <span>Street Address:</span>{" "}
                  {(profile as CustomerModel).streetAddress}
                </div>
                <div className="detail-row">
                  <span>City:</span> {(profile as CustomerModel).city}
                </div>
                <div className="detail-row">
                  <span>Province:</span> {(profile as CustomerModel).province}
                </div>
                <div className="detail-row">
                  <span>Postal Code:</span>{" "}
                  {(profile as CustomerModel).postalCode}
                </div>
                <div className="detail-row">
                  <span>Country:</span> {(profile as CustomerModel).country}
                </div>
              </>
            )}

            {userType === "Employee" && (
              <>
                <div className="detail-row">
                  <span>First Name:</span>{" "}
                  {(profile as EmployeeModel).first_name}
                </div>
                <div className="detail-row">
                  <span>Last Name:</span> {(profile as EmployeeModel).last_name}
                </div>
                <div className="detail-row">
                  <span>Position:</span> {(profile as EmployeeModel).position}
                </div>
                <div className="detail-row">
                  <span>Email:</span> {(profile as EmployeeModel).email}
                </div>
                <div className="detail-row">
                  <span>Phone:</span> {(profile as EmployeeModel).phone}
                </div>
                <div className="detail-row">
                  <span>Availability:</span>
                  <ul>
                    {(profile as EmployeeModel).availability.map(
                      (avail, index) => (
                        <li key={index}>
                          {avail.dayOfWeek}: {avail.startTime} - {avail.endTime}
                        </li>
                      ),
                    )}
                  </ul>
                </div>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
