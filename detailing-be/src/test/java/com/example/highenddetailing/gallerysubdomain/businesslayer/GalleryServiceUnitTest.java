package com.example.highenddetailing.gallerysubdomain.businesslayer;

import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.datalayer.GalleryIdentifier;
import com.example.highenddetailing.gallerysubdomain.datalayer.GalleryRepository;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryResponseModel;
import com.example.highenddetailing.gallerysubdomain.mapperlayer.GalleryResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GalleryServiceUnitTest {

    @Mock
    private GalleryRepository galleryRepository;

    @Mock
    private GalleryResponseMapper galleryResponseMapper;

    @InjectMocks
    private GalleryServiceImpl galleryService;

    @Test
    void whenGetAllGalleries_thenReturnAllGalleries() {
        // Arrange
        Gallery gallery1 = new Gallery();
        gallery1.setId(1);
        gallery1.setGalleryIdentifier(new GalleryIdentifier());
        gallery1.setDescription("Car Exterior 1");
        gallery1.setImageUrl("gallery1.jpg");

        Gallery gallery2 = new Gallery();
        gallery2.setId(2);
        gallery2.setGalleryIdentifier(new GalleryIdentifier());
        gallery2.setDescription("Car Interior 2");
        gallery2.setImageUrl("gallery2.jpg");

        List<Gallery> galleries = List.of(gallery1, gallery2);
        List<GalleryResponseModel> responseModels = List.of(
                new GalleryResponseModel(gallery1.getGalleryIdentifier().getGalleryId(), "Car Exterior 1", "gallery1.jpg"),
                new GalleryResponseModel(gallery2.getGalleryIdentifier().getGalleryId(), "Car Interior 2", "gallery2.jpg")
        );

        when(galleryRepository.findAll()).thenReturn(galleries);
        when(galleryResponseMapper.entityListToResponseModel(galleries)).thenReturn(responseModels);

        // Act
        List<GalleryResponseModel> galleryResponse = galleryService.getAllGalleries();

        // Assert
        assertEquals(2, galleryResponse.size());

        assertEquals(gallery1.getGalleryIdentifier().getGalleryId(), galleryResponse.get(0).getGalleryId());
        assertEquals("Car Exterior 1", galleryResponse.get(0).getDescription());
        assertEquals("gallery1.jpg", galleryResponse.get(0).getImageUrl());

        assertEquals(gallery2.getGalleryIdentifier().getGalleryId(), galleryResponse.get(1).getGalleryId());
        assertEquals("Car Interior 2", galleryResponse.get(1).getDescription());
        assertEquals("gallery2.jpg", galleryResponse.get(1).getImageUrl());
    }
}
