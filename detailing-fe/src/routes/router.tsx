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
import { EmployeeOnboardingForm } from "../models/EmployeeOnboardingForm";
import { EmployeeInviteSuccessPage } from "../models/EmployeeInviteSuccessPage";
import HomeCallbackHandler from "../pages/HomeCallbackHandler";
import CustomerDetailsPage from "../pages/CustomerDetailsPage";
import { MyAppointmentDetails } from "../pages/MyAppointmentDetails";
import PromotionForm from "../pages/PromotionForm";
import AboutUsPage from "../pages/AboutUsPage";

const router = createBrowserRouter([
  {
    path: AppRoutePath.Default,
    element: <Navigate to={AppRoutePath.Home} replace />,
  },
  {
    path: AppRoutePath.Onboarding,
    element: <OnboardingForm />,
  },
  {
    path: AppRoutePath.Home,
    element: <HomeCallbackHandler />,
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
    path: AppRoutePath.Profile,
    element: <ProfilePage />,
  },
  {
    path: "/employee-invite/:token",
    element: <EmployeeOnboardingForm />,
  },
  {
    path: "/employee-invite-success",
    element: <EmployeeInviteSuccessPage />,
  },
  {
    path: "/customers/:customerId",
    element: <CustomerDetailsPage />,
  },
  {
    path: "/my-appointments/:appointmentId",
    element: <MyAppointmentDetails />,
  },
  {
    path: AppRoutePath.Promotions,
    element: <PromotionForm />,
  },
  {
    path: "/about",
    element: <AboutUsPage />,
  }
]);

export default router;
