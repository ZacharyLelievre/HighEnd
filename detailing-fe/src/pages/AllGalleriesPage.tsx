import { NavBar} from "../nav/NavBar";
import AllGalleries from "../models/AllGalleries";

export default function AllGalleriesPage(): JSX.Element{
    return(
        <div>
            <NavBar />
            <AllGalleries />
        </div>
    )
}
export {};