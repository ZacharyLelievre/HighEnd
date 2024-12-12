import CustomerDetails from "../models/CustomerDetails";
import React from "react";
import {NavBar} from "../nav/NavBar";


export default function CustomerDetailsPage(): JSX.Element{
    return(
        <div>
            <NavBar />
            <CustomerDetails />
        </div>
    )
}