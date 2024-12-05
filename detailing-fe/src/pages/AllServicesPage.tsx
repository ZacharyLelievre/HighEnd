import { NavBar} from "../nav/NavBar";
import AllServices from "../models/AllServices";

export default function AllServicesPage(): JSX.Element{
    return(
        <div>
            <NavBar />
            <AllServices />
        </div>
    )
}