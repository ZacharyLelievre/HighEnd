import { NavBar } from "../nav/NavBar";
import AllServices from "../models/AllServices";
import React from "react";

export default function AllServicesPage(): JSX.Element {
  return (
    <div>
      <NavBar />
      <AllServices />
    </div>
  );
}
