import { GalleryModel } from "./dtos/GalleryModel";
import { useEffect, useState } from "react";
import axios from "axios";
import "./AllGalleries.css";

export default function AllGalleries(): JSX.Element {
    const [galleries, setGalleries] = useState<GalleryModel[]>([]);

    useEffect(()=> {
        const fetchGalleries = async (): Promise<void> => {
            try {
                const response = await axios.get("http://localhost:8080/api/galleries");
                setGalleries(response.data);
            } catch (error){
                console.error("Error fetching galleries", error);
            }
        };

        fetchGalleries();
    }, []);


    return(

        <div>
        <h2 style={{ textAlign: "center" }}>Gallery</h2>

        <div className="gallery">

        {galleries.map(gallery => (

            <div className="gallery-item" key={gallery.galleryId}>
                <img
                    className="gallery-image"
                    src={`${gallery.imageUrl}`}
                    alt={gallery.description}
                />
          
            </div>

        ))}

        </div>
        </div>

  
    );

}

export {};