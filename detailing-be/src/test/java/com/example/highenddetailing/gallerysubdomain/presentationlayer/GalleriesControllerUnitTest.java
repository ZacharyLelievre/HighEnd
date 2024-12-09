package com.example.highenddetailing.gallerysubdomain.presentationlayer;

import com.example.highenddetailing.gallerysubdomain.businesslayer.GalleryService;
import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.datalayer.GalleryIdentifier;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryController;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GalleryController.class)
public class GalleriesControllerUnitTest {

    @Autowired
    private MockMvc mockMvc; // Inject MockMvc to simulate HTTP requests

    @MockBean
    private GalleryService galleryService; // Mock the GalleryService

    private List<Gallery> galleries;
    private List<GalleryResponseModel> galleryResponseModels;

    @BeforeEach
    public void setup() {
        galleries = Arrays.asList(
                new Gallery(new GalleryIdentifier(), "Description of gallery 1", "/images/gallery/gallery1.jpg"),
                new Gallery(new GalleryIdentifier(), "Description of gallery 2", "/images/gallery/gallery2.jpg")
        );

        galleryResponseModels = Arrays.asList(
                GalleryResponseModel.builder()
                        .galleryId("1")  // Example ID
                        .description("Description of gallery 1")  // Example Description
                        .imageUrl("/images/gallery/gallery1.jpg")  // Example Image URL
                        .build(),
                GalleryResponseModel.builder()
                        .galleryId("2")
                        .description("Description of gallery 2")
                        .imageUrl("/images/gallery/gallery2.jpg")
                        .build());
    }

    @Test
    public void whenGetAllGalleries_thenReturnAllGalleries() throws Exception {
        // Mock the service to return predefined data
        when(galleryService.getAllGalleries()).thenReturn(galleryResponseModels);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/galleries"))
                .andExpect(status().isOk())  // Assert the status is 200 OK
                .andExpect(jsonPath("$.size()").value(2));  // Assert there are 2 galleries in the response
    }
}
