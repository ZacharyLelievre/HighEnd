import React from "react";
import { Outlet } from "react-router-dom";
import { NavBar } from "../nav/NavBar";

export function RootLayout(): JSX.Element {
    return (
        <>
            <NavBar />
            <div style={{ marginTop: "80px" }}>
                {/* Your routes will render below the NavBar */}
                <Outlet />
            </div>
        </>
    );
}