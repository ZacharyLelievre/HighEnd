import { createBrowserRouter } from "react-router-dom";
import { AppRoutePath } from "./path.routes";
import AllServicesPage from "../pages/AllServicesPage";
import Home from "../pages/Home";
import React from "react";
import ServiceDetailPage from "../pages/ServiceDetailsPage";
import AllGalleriesPage from "../pages/AllGalleriesPage";
import DashboardPage from "../pages/DashhoardPage";
import EmployeeDetails from "../models/EmployeeDetails";
import { OnboardingForm } from "../pages/OnboardingForm";
import { ProfilePage } from "../pages/ProfilePage";
import { Navigate } from "react-router-dom";
import CustomerDetailsPage from "../pages/CustomerDetailsPage";

const router = createBrowserRouter([
  {
    path: AppRoutePath.Default,
    element: <Navigate to={AppRoutePath.Home} replace={true} />,
  },
  {
    path: AppRoutePath.Onboarding,
    element: <OnboardingForm />,
  },
  {
    path: AppRoutePath.Home,
    element: <Home />,
  },
  {
    path: AppRoutePath.AllServicesPage,
    element: <AllServicesPage />,
  },
  {
    path: "/services/:serviceId",
    element: <ServiceDetailPage />,
  },
  {
    path: AppRoutePath.AllGalleriesPage,
    element: <AllGalleriesPage />,
  },
  {
    path: AppRoutePath.DashboardPage,
    element: <DashboardPage />,
  },
  {
    path: "/employees/:employeeId",
    element: <EmployeeDetails />,
  },
  {
    path: AppRoutePath.CustomerDetails,
    element: <CustomerDetailsPage />,
  },
  {
    path: AppRoutePath.Profile,
    element: <ProfilePage />,
  },
]);

export default router;