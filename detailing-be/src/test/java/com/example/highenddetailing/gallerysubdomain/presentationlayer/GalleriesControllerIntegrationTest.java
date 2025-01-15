package com.example.highenddetailing.gallerysubdomain.presentationlayer;

import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.datalayer.GalleryIdentifier;
import com.example.highenddetailing.gallerysubdomain.datalayer.GalleryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GalleriesControllerIntegrationTest {

    @LocalServerPort
    private int port;  // This will hold the random port assigned to the embedded server

    @Autowired
    private GalleryRepository galleryRepository;  // The gallery repository for database interactions

    private RestTemplate restTemplate;  // RestTemplate for making HTTP requests

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();  // Initialize RestTemplate
    }

    @BeforeEach
    public void initData() {
        // Insert mock data into the database (if needed)
        galleryRepository.saveAll(Arrays.asList(
                new Gallery(new GalleryIdentifier(), "Description of gallery 1", "/images/gallery/gallery1.jpg"),
                new Gallery(new GalleryIdentifier(), "Description of gallery 2", "/images/gallery/gallery2.jpg"),
                new Gallery(new GalleryIdentifier(), "Description of gallery 3", "/images/gallery/gallery3.jpg"),
                new Gallery(new GalleryIdentifier(), "Description of gallery 4", "/images/gallery/gallery4.jpg"),
                new Gallery(new GalleryIdentifier(), "Description of gallery 5", "/images/gallery/gallery5.jpg"),
                new Gallery(new GalleryIdentifier(), "Description of gallery 6", "/images/gallery/gallery6.jpg"),
                new Gallery(new GalleryIdentifier(), "Description of gallery 7", "/images/gallery/gallery7.jpg"),
                new Gallery(new GalleryIdentifier(), "Description of gallery 8", "/images/gallery/gallery8.jpg"),
                new Gallery(new GalleryIdentifier(), "Description of gallery 9", "/images/gallery/gallery9.jpg"),
                new Gallery(new GalleryIdentifier(), "Description of gallery 10", "/images/gallery/gallery10.jpg")
        ));
    }

//    @Test
//    public void whenGetAllGalleries_thenReturnAllGalleries() {
//        // Construct the URL for the galleries
//        String url = "http://localhost:" + port + "/api/galleries";  // Use the random port assigned to the app
//
//        // Make a GET request to the API
//        ResponseEntity<List> response = restTemplate.exchange(
//                url,
//                org.springframework.http.HttpMethod.GET,
//                null,
//                List.class
//        );
//
//        // Assert that the response is OK and contains 10 galleries
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(10, response.getBody().size());
//    }

    // Add more tests for other endpoints or error cases if necessary...
}
