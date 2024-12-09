import { createBrowserRouter, Navigate } from "react-router-dom";
import { AppRoutePath } from "./path.routes";
import AllServicesPage from "../pages/AllServicesPage";
import AllAppointmentsPage from "../pages/AllAppointmentsPage";
import Home from "../models/Home";
import React from "react";
import ServiceDetailPage from "../pages/ServiceDetailsPage";

const router = createBrowserRouter([
    {
        children: [
            {
                path: AppRoutePath.Default,
                element: <Navigate to={AppRoutePath.Home} replace />
            },
            {
                path: AppRoutePath.Home,
                element: <Home />
            },
            {
                path: AppRoutePath.AllServicesPage,
                element: <AllServicesPage />
            },
            {
                path: "/services/:serviceId",
                element: <ServiceDetailPage />
            },
            {
                path: AppRoutePath.AllAppointmentsPage,
                element: <AllAppointmentsPage />
            }
        ]
    }
])

export default router;