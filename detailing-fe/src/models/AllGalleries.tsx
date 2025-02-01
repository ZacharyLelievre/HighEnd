import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";
import { Pagination, Navigation, Autoplay } from "swiper/modules";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { GalleryModel } from "./dtos/GalleryModel";
import "./AllGalleries.css";

export default function AllGalleries(): JSX.Element {
  const [galleries, setGalleries] = useState<GalleryModel[]>([]);
  const [selectedImage, setSelectedImage] = useState<string | null>(null);

  useEffect(() => {
    const fetchGalleries = async (): Promise<void> => {
      try {
        const response = await axios.get(
          "https://highend-zke6.onrender.com/api/galleries",
        );
        setGalleries(response.data);
      } catch (error) {
        console.error("Error fetching galleries", error);
      }
    };

    fetchGalleries();
  }, []);

  return (
    <div className="gallery-container">
      <h2 className="gallery-title" style={{ textAlign: "center", color: "white" }}>Gallery</h2>
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
            <img
              className="gallery-image"
              src={gallery.imageUrl}
              alt={gallery.description}
              onClick={() => setSelectedImage(gallery.imageUrl)}
            />
          </SwiperSlide>
        ))}
      </Swiper>

      {/* Lightbox (Full-Screen Preview) */}
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
    </div>
  );
}
