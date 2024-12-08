import React from "react";
import { NavBar } from "../nav/NavBar";
import ServiceDetail from "../models/ServiceDetails";

export default function ServiceDetailPage(): JSX.Element {
    return (
        <div>
            <NavBar />
            <ServiceDetail />
        </div>
    );
}
