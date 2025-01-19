import React from "react";
import {CustomerModel} from "../models/dtos/CustomerModel";

interface CustomerDetailsProps {
    customer: CustomerModel;
    onClose: () => void;
}

export default function CustomerDetails({
                                            customer,
                                            onClose,
                                        }: CustomerDetailsProps): JSX.Element {
    return (
        <div className="modal-overlay">
            <div className="modal">
                <div className="modal-header">
                    <h3>Customer Details</h3>
                    <button className="close-btn" onClick={onClose}>
                        X
                    </button>
                </div>
                <div className="modal-content">
                    <p>
                        <strong>Name:</strong> {customer.customerFirstName}{" "}
                        {customer.customerLastName}
                    </p>
                    <p>
                        <strong>Email:</strong> {customer.customerEmailAddress}
                    </p>
                    <p>
                        <strong>Address:</strong> {customer.streetAddress}, {customer.city},{" "}
                        {customer.postalCode}, {customer.province}, {customer.country}
                    </p>
                </div>
            </div>
        </div>
    );
}