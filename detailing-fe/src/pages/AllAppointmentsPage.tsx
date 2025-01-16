import AllAppointments from "../models/AllAppointments";
import React from "react";
import { NavBar } from "../nav/NavBar";

export default function AllServicesPage(): JSX.Element {
  return (
    <div>
      <NavBar />
      <AllAppointments />
    </div>
  );
}
