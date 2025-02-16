import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";
import { Pagination, Navigation, Autoplay } from "swiper/modules";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { GalleryModel } from "./dtos/GalleryModel";
import "./AllGalleries.css";
import { toast, ToastContainer } from "react-toastify";  // Added ToastContainer import
import "react-toastify/dist/ReactToastify.css";
import { useAuth0 } from "@auth0/auth0-react"; // Import useAuth0

export default function AllGalleries(): JSX.Element {
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;
  const { getAccessTokenSilently } = useAuth0(); // Get access token to check user roles

  const [galleries, setGalleries] = useState<GalleryModel[]>([]);
  const [selectedImage, setSelectedImage] = useState<string | null>(null);
  const [isAdmin, setIsAdmin] = useState<boolean>(false);

  useEffect(() => {
    const fetchGalleries = async (): Promise<void> => {
      try {
        const response = await axios.get(`${apiBaseUrl}/galleries`);
        setGalleries(response.data);
      } catch (error) {
        console.error("Error fetching galleries", error);
      }
    };

    fetchGalleries();
  }, []);

  useEffect(() => {
    const checkIfUserIsAdmin = async () => {
      try {
        const accessToken = await getAccessTokenSilently();
        const base64Url = accessToken.split(".")[1];
        const decodedPayload = atob(base64Url);
        const tokenData = JSON.parse(decodedPayload);
        const roles = tokenData["https://highenddetailing/roles"] || tokenData.roles || [];
        setIsAdmin(roles.includes("ADMIN"));
      } catch (error) {
        console.error("Error fetching access token or roles:", error);
      }
    };

    checkIfUserIsAdmin();
  }, [getAccessTokenSilently]);

  const deleteGallery = async (galleryId: string) => {
    try {
      await axios.delete(`http://localhost:8081/api/galleries/${galleryId}`);
      setGalleries(galleries.filter(gallery => gallery.galleryId !== galleryId));
      toast.success("Image deleted successfully");
    } catch (error) {
      console.error("Error deleting image", error);
      toast.error("Failed to delete image");
    }
  };

  return (
    <div className="gallery-container" style={{ backgroundColor: "black" }}>
      <h2 className="gallery-title" style={{ textAlign: "center", color: "white" }}>
        Gallery
      </h2>
      <Swiper
        modules={[Pagination, Navigation, Autoplay]}
        spaceBetween={20}
        slidesPerView={3}
        pagination={{ clickable: true }}
        navigation
        autoplay={{ delay: 3000, disableOnInteraction: false }}
        breakpoints={{
          320: { slidesPerView: 1 },
          768: { slidesPerView: 2 },
          1024: { slidesPerView: 3 },
        }}
        className="swiper-container"
      >
        {galleries.map((gallery) => (
          <SwiperSlide key={gallery.galleryId} className="swiper-slide">
            <div className="gallery-item">
              <img
                className="gallery-image"
                src={gallery.imageUrl}
                alt={gallery.description}
                onClick={() => setSelectedImage(gallery.imageUrl)}
              />
              {isAdmin && (
                <button className="delete-button" onClick={() => deleteGallery(gallery.galleryId)}>
                  Delete
                </button>
              )}
            </div>
          </SwiperSlide>
        ))}
      </Swiper>

      {/* Lightbox (Full-Screen Preview) */}
      {selectedImage && (
        <div className="lightbox" onClick={() => setSelectedImage(null)}>
          <div className="lightbox-content">
            <button className="close-button" onClick={() => setSelectedImage(null)}>
              ✖
            </button>
            <img className="lightbox-image" src={selectedImage} alt="Full Preview" />
          </div>
        </div>
      )}

      <ToastContainer /> {/* Add this ToastContainer to display the toasts */}
    </div>
  );
}
