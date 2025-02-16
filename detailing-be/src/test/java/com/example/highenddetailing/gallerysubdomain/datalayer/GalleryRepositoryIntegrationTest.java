package com.example.highenddetailing.gallerysubdomain.datalayer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GalleryRepositoryIntegrationTest {

    @Autowired
    private GalleryRepository galleryRepository;

    @Test
    void whenFindAll_thenReturnAllGalleries() {
        // Arrange: Prepare sample galleries
        Gallery gallery1 = new Gallery();
        gallery1.setGalleryIdentifier(new GalleryIdentifier());
        gallery1.setDescription("Car Exterior 1");
        gallery1.setImageUrl("gallery1.jpg");

        Gallery gallery2 = new Gallery();
        gallery2.setGalleryIdentifier(new GalleryIdentifier());
        gallery2.setDescription("Car Interior 2");
        gallery2.setImageUrl("gallery2.jpg");

        galleryRepository.save(gallery1);
        galleryRepository.save(gallery2);

        // Act: Fetch all galleries
        List<Gallery> galleries = galleryRepository.findAll();

        // Assert: Verify the results
        assertNotNull(galleries);
        assertEquals(2, galleries.size());

        // Verify the first gallery
        assertEquals(gallery1.getGalleryIdentifier().getGalleryId(), galleries.get(0).getGalleryIdentifier().getGalleryId());
        assertEquals("Car Exterior 1", galleries.get(0).getDescription());
        assertEquals("gallery1.jpg", galleries.get(0).getImageUrl());

        // Verify the second gallery
        assertEquals(gallery2.getGalleryIdentifier().getGalleryId(), galleries.get(1).getGalleryIdentifier().getGalleryId());
        assertEquals("Car Interior 2", galleries.get(1).getDescription());
        assertEquals("gallery2.jpg", galleries.get(1).getImageUrl());
    }



    @Test
    void whenDeleteImage_thenGalleryIsRemoved() {
        // Arrange: Create and save a gallery
        Gallery gallery = new Gallery();
        GalleryIdentifier identifier = new GalleryIdentifier();
        gallery.setGalleryIdentifier(identifier);
        gallery.setDescription("Car Exterior");
        gallery.setImageUrl("gallery.jpg");

        gallery = galleryRepository.saveAndFlush(gallery);  // Ensure it is saved to the DB
        String galleryId = gallery.getGalleryIdentifier().getGalleryId();

        assertNotNull(galleryId, "Gallery ID should not be null after saving");
        assertTrue(galleryRepository.findByGalleryIdentifier_GalleryId(galleryId).isPresent(), "Gallery should exist before deletion");

        // Act: Delete the gallery
        galleryRepository.delete(gallery);
        galleryRepository.flush();  // Ensure the deletion is committed

        // Assert: Ensure the gallery no longer exists
        assertFalse(galleryRepository.findByGalleryIdentifier_GalleryId(galleryId).isPresent(), "Gallery should be removed after deletion");
    }

}
