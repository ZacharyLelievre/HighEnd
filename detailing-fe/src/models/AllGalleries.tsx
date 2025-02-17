import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";
import { Pagination, Navigation, Autoplay } from "swiper/modules";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { GalleryModel } from "./dtos/GalleryModel";
import "./AllGalleries.css";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAuth0 } from "@auth0/auth0-react";

export default function AllGalleries(): JSX.Element {
  const apiBaseUrl = process.env.REACT_APP_API_BASE_URL;
  const { getAccessTokenSilently } = useAuth0();

  const [galleries, setGalleries] = useState<GalleryModel[]>([]);
  const [selectedImage, setSelectedImage] = useState<string | null>(null);
  const [isAdmin, setIsAdmin] = useState<boolean>(false);
  const [file, setFile] = useState<File | null>(null);
  const [description, setDescription] = useState<string>("");

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
        const roles =
          tokenData["https://highenddetailing/roles"] || tokenData.roles || [];
        setIsAdmin(roles.includes("ADMIN"));
      } catch (error) {
        console.error("Error fetching access token or roles:", error);
      }
    };

    checkIfUserIsAdmin();
  }, [getAccessTokenSilently]);

  const deleteGallery = async (galleryId: string) => {
    try {
      await axios.delete(`${apiBaseUrl}/galleries/${galleryId}`);
      setGalleries(
        galleries.filter((gallery) => gallery.galleryId !== galleryId),
      );
      toast.success("Image deleted successfully");
    } catch (error) {
      console.error("Error deleting image", error);
      toast.error("Failed to delete image");
    }
  };

  const uploadImage = async () => {
    if (!file || !description) {
      toast.error("Please select a file and enter a description.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("description", description);

    try {
      const accessToken = await getAccessTokenSilently();
      const response = await axios.post(
        `${apiBaseUrl}/galleries/upload`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "multipart/form-data",
          },
        },
      );

      toast.success("Image uploaded successfully");
      setGalleries([...galleries, response.data]); // Add new image to state
      setFile(null);
      setDescription("");
      window.location.reload();
    } catch (error) {
      console.error("Error uploading image", error);
      toast.error("Failed to upload image");
    }
  };

  const cancelUpload = () => {
    setFile(null);
    setDescription("");
  };

  return (
    <div className="gallery-container">
      <h2
        className="gallery-title"
        style={{ textAlign: "center", color: "white" }}
      >
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
                <button
                  className="delete-button"
                  onClick={() => deleteGallery(gallery.galleryId)}
                >
                  Delete
                </button>
              )}
            </div>
          </SwiperSlide>
        ))}
      </Swiper>

      {isAdmin && (
        <div className="upload-container" style={{ backgroundColor: "black" }}>
          <h3 className="upload-title">Upload New Image</h3>
          <input
            type="file"
            accept="image/*"
            onChange={(e) => setFile(e.target.files ? e.target.files[0] : null)}
          />
          <input
            type="text"
            placeholder="Enter description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
          <div className="button-group">
            <button className="upload-button" onClick={uploadImage}>
              Upload
            </button>
            <button className="cancel-button" onClick={cancelUpload}>
              Cancel
            </button>
          </div>
        </div>
      )}

      {selectedImage && (
        <div className="lightbox" onClick={() => setSelectedImage(null)}>
          <div className="lightbox-content">
            <button
              className="close-button"
              onClick={() => setSelectedImage(null)}
            >
              ✖
            </button>
            <img
              className="lightbox-image"
              src={selectedImage}
              alt="Full Preview"
            />
          </div>
        </div>
      )}

      {/* Google Reviews Widget */}
      <div
        className="google-reviews-widget"
        style={{ marginTop: "50px", textAlign: "center" }}
      >
        <h3>What our customers are saying:</h3>
        <script src="https://static.elfsight.com/platform/platform.js" async></script>
        <div className="elfsight-app-38317100-95ae-480a-9fbd-3dc4a5267fc2" data-elfsight-app-lazy></div>
      </div>

      <ToastContainer />
    </div>
  );
}
