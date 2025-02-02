import { NavBar } from "../nav/NavBar";
import AllServices from "../models/AllServices";
import { Footer } from "./Footer";
import React from "react";

export default function AllServicesPage(): JSX.Element {
  return (
    <div>
      <NavBar />
      <AllServices />
      <Footer />
    </div>
  );
}
