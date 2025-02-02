import { NavBar } from "../nav/NavBar";
import AllGalleries from "../models/AllGalleries";
import { Footer } from "./Footer";
import React from "react";

export default function AllGalleriesPage(): JSX.Element {
  return (
    <div>
      <NavBar />
      <AllGalleries />
      <Footer />
    </div>
  );
}
export {};
