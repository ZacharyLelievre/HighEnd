import { createBrowserRouter, Navigate } from "react-router-dom";
import { AppRoutePath } from "./path.routes";
import AllServicesPage from "../pages/AllServicesPage";
import Home from "../models/Home";
import React from "react";
import ServiceDetailPage from "../pages/ServiceDetailsPage";
import AllGalleriesPage from "../pages/AllGalleriesPage";
import DashboardPage from "../pages/DashhoardPage";
import EmployeeDetails from "../models/EmployeeDetails";

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
                path: AppRoutePath.AllGalleriesPage,
                element: <AllGalleriesPage />
            },
            {
                path: AppRoutePath.DashboardPage,
                element: <DashboardPage />
            }
            ,
            {
                path: "/employees/:employeeId",
                element: <EmployeeDetails />
            }
        ]
    }
])

export default router;