import React from "react";
import { NavBar } from "../nav/NavBar";
import CustomerDetails from "../models/CustomerDetails";

export default function CustomerDetailsPage(): JSX.Element {
    return (
        <>
            <NavBar />
            <CustomerDetails />
        </>
    );
}