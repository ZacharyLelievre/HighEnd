package com.example.highenddetailing.gallerysubdomain.presentationlayer;

import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.datalayer.GalleryIdentifier;
import com.example.highenddetailing.gallerysubdomain.datalayer.GalleryRepository;
import com.example.highenddetailing.gallerysubdomain.businesslayer.GalleryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GalleriesControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private GalleryRepository galleryRepository;

    @MockBean
    private GalleryService galleryService;

    private RestTemplate restTemplate;
    private String baseUrl;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
        baseUrl = "http://localhost:" + port + "/galleries";
        galleryRepository.deleteAll();
    }

    @BeforeEach
    public void initData() {
        galleryRepository.saveAll(Arrays.asList(
                new Gallery(new GalleryIdentifier(), "Description of gallery 1", "/images/gallery/gallery1.jpg"),
                new Gallery(new GalleryIdentifier(), "Description of gallery 2", "/images/gallery/gallery2.jpg"),
                new Gallery(new GalleryIdentifier(), "Description of gallery 3", "/images/gallery/gallery3.jpg")
        ));
    }

//    @Test
//    public void deleteGallery_existingId_shouldReturnNoContent() {
//        String galleryId = galleryRepository.findAll().get(0).getGalleryIdentifier().getGalleryId();
//
//        doNothing().when(galleryService).deleteImage(galleryId);
//
//        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/" + galleryId, HttpMethod.DELETE, null, Void.class);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(galleryService, times(1)).deleteImage(galleryId);
//    }
//
//    @Test
//    public void deleteGallery_nonExistingId_shouldReturnNotFound() {
//        String galleryId = "nonExistingId";
//
//        doThrow(new RuntimeException("Gallery not found")).when(galleryService).deleteImage(galleryId);
//
//        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/" + galleryId, HttpMethod.DELETE, null, Void.class);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(galleryService, times(1)).deleteImage(galleryId);
//    }
}