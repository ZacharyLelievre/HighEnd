import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import axios from "axios";
import { NavBar } from "../nav/NavBar";
import "./ProfilePage.css";

interface CustomerInfo {
  customerFirstName: string;
  customerLastName: string;
  customerEmailAddress: string;
  streetAddress: string;
  city: string;
  province: string;
  postalCode: string;
  country: string;
}

export function ProfilePage() {
  const { getAccessTokenSilently, user } = useAuth0();
  const [profile, setProfile] = useState<CustomerInfo | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        setLoading(true);
        const token = await getAccessTokenSilently();
        const response = await axios.get<CustomerInfo>(
          "https://highend-zke6.onrender.com/api/customers/me",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          },
        );
        setProfile(response.data);
      } catch (error) {
        console.error("Error fetching profile:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchProfile();
  }, [getAccessTokenSilently]);

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

  if (!profile) {
    return (
      <div>
        <NavBar />
        <div className="profile-container">
          <p>No profile information available.</p>
        </div>
      </div>
    );
  }

  return (
    <div>
      <NavBar />
      <div className="profile-container">
        <div className="profile-card">
          <div className="profile-header">
            <div className="profile-avatar">
              <img
                src={user?.picture || "https://via.placeholder.com/100"}
                alt={`${profile.customerFirstName} ${profile.customerLastName}`}
              />
            </div>
            <div className="profile-name">
              <h2>{`${profile.customerFirstName} ${profile.customerLastName}`}</h2>
              <p className="profile-email">{profile.customerEmailAddress}</p>
            </div>
          </div>
          <div className="profile-details">
            <div className="detail-row">
              <span>Street Address:</span> {profile.streetAddress}
            </div>
            <div className="detail-row">
              <span>City:</span> {profile.city}
            </div>
            <div className="detail-row">
              <span>Province:</span> {profile.province}
            </div>
            <div className="detail-row">
              <span>Postal Code:</span> {profile.postalCode}
            </div>
            <div className="detail-row">
              <span>Country:</span> {profile.country}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
