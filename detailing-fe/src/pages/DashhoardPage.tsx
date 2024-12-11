import { NavBar } from "../nav/NavBar";
import AllCustomers from "../models/AllCustomers";

export default function DashboardPage(): JSX.Element{

    return (
        <div>
            <NavBar/>
            <AllCustomers/>
        </div>
    )
}