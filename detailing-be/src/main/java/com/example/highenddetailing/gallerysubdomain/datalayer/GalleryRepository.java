package com.example.highenddetailing.gallerysubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Integer> {

    Optional<Gallery> findByGalleryIdentifier_GalleryId(String galleryId);

}
