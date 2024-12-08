package com.example.highenddetailing.gallerysubdomain.presentationlayer;

import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.datalayer.GalleryIdentifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GalleryResponseModelTest {

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        GalleryIdentifier identifier = new GalleryIdentifier();
        Gallery gallery1 = new Gallery();
        gallery1.setId(1);
        gallery1.setGalleryIdentifier(identifier);
        gallery1.setDescription("Car Exterior 1");
        gallery1.setImageUrl("gallery1.jpg");

        Gallery gallery2 = new Gallery();
        gallery2.setId(1);
        gallery2.setGalleryIdentifier(identifier);
        gallery2.setDescription("Car Exterior 1");
        gallery2.setImageUrl("gallery1.jpg");

        Gallery gallery3 = new Gallery();
        gallery3.setId(2);
        gallery3.setGalleryIdentifier(new GalleryIdentifier());
        gallery3.setDescription("Car Interior 1");
        gallery3.setImageUrl("gallery2.jpg");

        // Act & Assert
        assertEquals(gallery1, gallery2);
        assertEquals(gallery1.hashCode(), gallery2.hashCode());
        assertThat(gallery1).isNotEqualTo(gallery3);
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Gallery gallery = new Gallery();
        GalleryIdentifier identifier = new GalleryIdentifier();

        // Act
        gallery.setId(1);
        gallery.setGalleryIdentifier(identifier);
        gallery.setDescription("Car Exterior 1");
        gallery.setImageUrl("gallery1.jpg");

        // Assert
        assertEquals(gallery.getId(), 1);
        assertEquals(gallery.getGalleryIdentifier(), identifier);
        assertEquals(gallery.getDescription(), "Car Exterior 1");
        assertEquals(gallery.getImageUrl(), "gallery1.jpg");
    }

    @Test
    void testConstructorWithAllArgs() {
        // Arrange
        GalleryIdentifier identifier = new GalleryIdentifier();
        Gallery gallery = new Gallery();
        gallery.setId(1);
        gallery.setGalleryIdentifier(identifier);
        gallery.setDescription("Car Exterior 1");
        gallery.setImageUrl("gallery1.jpg");

        // Assert
        assertEquals(gallery.getId(), 1);
        assertEquals(gallery.getGalleryIdentifier(), identifier);
        assertEquals(gallery.getDescription(), "Car Exterior 1");
        assertEquals(gallery.getImageUrl(), "gallery1.jpg");
    }

    @Test
    void testGalleryBuilderToString() {
        // Arrange
        Gallery gallery = new Gallery();
        gallery.setId(1);
        gallery.setGalleryIdentifier(new GalleryIdentifier());
        gallery.setDescription("Car Exterior 1");
        gallery.setImageUrl("gallery1.jpg");

        // Act
        String toStringResult = gallery.toString();

        // Assert
        assertThat(toStringResult).contains(
                "1",
                "Car Exterior 1",
                "gallery1.jpg"
        );
    }

    @Test
    void testGalleryBuilderId() {
        // Arrange
        Gallery gallery = new Gallery();
        gallery.setId(1);

        // Act & Assert
        assertThat(gallery.getId()).isEqualTo(1);
    }

    @Test
    void testGalleryBuilderImageUrl() {
        // Arrange
        Gallery gallery = new Gallery();
        gallery.setImageUrl("gallery1.jpg");

        // Act & Assert
        assertThat(gallery.getImageUrl()).isEqualTo("gallery1.jpg");
    }

    @Test
    void testEquals() {
        // Arrange
        GalleryIdentifier identifier1 = new GalleryIdentifier();
        GalleryIdentifier identifier2 = new GalleryIdentifier();
        Gallery gallery1 = new Gallery();
        gallery1.setId(1);
        gallery1.setGalleryIdentifier(identifier1);
        gallery1.setDescription("Car Exterior 1");
        gallery1.setImageUrl("gallery1.jpg");

        Gallery gallery2 = new Gallery();
        gallery2.setId(1);
        gallery2.setGalleryIdentifier(identifier1);
        gallery2.setDescription("Car Exterior 1");
        gallery2.setImageUrl("gallery1.jpg");

        Gallery gallery3 = new Gallery();
        gallery3.setId(2);
        gallery3.setGalleryIdentifier(identifier2);
        gallery3.setDescription("Car Interior 1");
        gallery3.setImageUrl("gallery2.jpg");

        // Act & Assert
        assertThat(gallery1).isEqualTo(gallery2);
        assertThat(gallery1).isNotEqualTo(gallery3);
    }

    @Test
    void testHashCode() {
        // Arrange
        GalleryIdentifier identifier1 = new GalleryIdentifier();
        GalleryIdentifier identifier2 = new GalleryIdentifier();
        Gallery gallery1 = new Gallery();
        gallery1.setId(1);
        gallery1.setGalleryIdentifier(identifier1);
        gallery1.setDescription("Car Exterior 1");
        gallery1.setImageUrl("gallery1.jpg");

        Gallery gallery2 = new Gallery();
        gallery2.setId(1);
        gallery2.setGalleryIdentifier(identifier1);
        gallery2.setDescription("Car Exterior 1");
        gallery2.setImageUrl("gallery1.jpg");

        Gallery gallery3 = new Gallery();
        gallery3.setId(2);
        gallery3.setGalleryIdentifier(identifier2);
        gallery3.setDescription("Car Interior 1");
        gallery3.setImageUrl("gallery2.jpg");

        // Act & Assert
        assertThat(gallery1.hashCode()).isEqualTo(gallery2.hashCode());
        assertThat(gallery1.hashCode()).isNotEqualTo(gallery3.hashCode());
    }
}

