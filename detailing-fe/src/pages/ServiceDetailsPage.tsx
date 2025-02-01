import React from "react";
import { NavBar } from "../nav/NavBar";
import ServiceDetail from "../models/ServiceDetails";
import {Footer} from "./Footer";

export default function ServiceDetailPage(): JSX.Element {
  return (
    <div>
      <NavBar />
      <ServiceDetail />
        <Footer />
    </div>
  );
}
