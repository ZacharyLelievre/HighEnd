import { NavBar } from "../nav/NavBar"
import AllAppointments from "./AllAppointments";


export default function Home(): JSX.Element {

    return (
        <div>
            <NavBar />
            <p> hello </p>
            <AllAppointments />
        </div>
    )
}