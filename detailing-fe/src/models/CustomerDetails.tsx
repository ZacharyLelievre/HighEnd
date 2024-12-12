
import './CustomerDetails.css'
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {CustomerModel} from "./dtos/CustomerModel";
import axios from "axios";


export default function CustomerDetails(): JSX.Element {
    const {customerId} = useParams<{customerId: string}>();
    const [customer, setCustomer] = useState<CustomerModel | null>(null);

    useEffect(() => {
        const fetchCustomerDetails = async (): Promise<void> => {
            try {
                const response = await axios.get(`http://localhost:8080/api/customers/${customerId}`);
                setCustomer(response.data);
            } catch (error) {
                console.error("Error fetching customer details: ", error)
            }
        };
        fetchCustomerDetails()
    }, [customerId]);

    if (!customer) {
        return <div>Loading...</div>
    }

    return (
        <div className="details-container">
            {/* Profile Header */}
            <div className="profile-header">
                <img src="https://via.placeholder.com/70" alt="Profile" />
                <div className="info">
                    <h2>{`${customer.customerFirstName} ${customer.customerLastName}`}</h2>
                    <p>{customer.customerEmailAddress}</p>
                </div>
            </div>
    
            {/* Details Section */}
            <div className="details">
                <div className="field">
                    <label>First Name</label>
                    <input type="text" value={`${customer.customerFirstName} ${customer.customerLastName}`} readOnly />
                </div>
                <div className="field">
                    <label>Last Name</label>
                    <input type="text" value={customer.customerLastName || "N/A"} readOnly />
                </div>
                <div className="field">
                    <label>Postal Code</label>
                    <input type="text" value={customer.postalCode || "N/A"} readOnly />
                </div>
                <div className="field">
                    <label>Address</label>
                    <input type="text" value={customer.streetAddress || "N/A"} readOnly />
                </div>
                <div className="field">
                    <label>City</label>
                    <input type="text" value={customer.city || "N/A"} readOnly />
                </div>
                <div className="field">
                    <label>Province</label>
                    <input type="text" value={customer.province || "N/A"} readOnly />
                </div>
                <div className="field">
                    <label>Country</label>
                    <input type="text" value={customer.country || "N/A"} readOnly />
                </div>
                
                
            </div>
    
            {/* Email Section */}
            <div className="email-section">
                <h3>My email Address</h3>
                <div className="email">
                    <p>{customer.customerEmailAddress}</p>
                    <span>1 month ago</span>
                </div>
                <button>+ Add Email Address</button>
            </div>
        </div>
    );  
}