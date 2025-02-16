package com.example.highenddetailing.promotionsubdomain.businesslayer;

import com.example.highenddetailing.promotionsubdomain.datalayer.Promotion;
import com.example.highenddetailing.promotionsubdomain.datalayer.PromotionRepository;
import com.example.highenddetailing.promotionsubdomain.mapperlayer.PromotionMapper;
import com.example.highenddetailing.promotionsubdomain.presentationlayer.PromotionRequestModel;
import com.example.highenddetailing.promotionsubdomain.presentationlayer.PromotionResponseModel;
import com.example.highenddetailing.servicessubdomain.datalayer.Service;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceIdentifier;
import com.example.highenddetailing.servicessubdomain.datalayer.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceUnitTest {

    @Mock
    private PromotionRepository promotionRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private PromotionMapper promotionMapper;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    private Service sampleService;
    private PromotionRequestModel sampleRequest;
    private Promotion samplePromotion;

    @BeforeEach
    void setUp() {
        // Create a sample Service entity
        sampleService = Service.builder()
                .id(1)  // the primary key
                .serviceIdentifier(new ServiceIdentifier())
                .serviceName("Sample Service")
                .timeRequired("30 minutes")
                .price(249.99f)
                .imagePath("sample.png")
                .build();

        // Create a sample PromotionRequest that references the service above
        sampleRequest = PromotionRequestModel.builder()
                .serviceId("service-123")
                .newPrice(199.99f)
                .discountMessage("Test Discount")
                .build();

        // Create a sample Promotion entity
        samplePromotion = Promotion.builder()
                .id(1L)
                .service(sampleService)
                .oldPrice(sampleService.getPrice())
                .newPrice(sampleRequest.getNewPrice())
                .discountMessage(sampleRequest.getDiscountMessage())
                .build();
    }

    @Test
    void createPromotion_serviceFound_savesAndReturnsResponse() {
        // Arrange
        when(serviceRepository.findByServiceIdentifier_ServiceId("service-123"))
                .thenReturn(Optional.of(sampleService));
        when(promotionRepository.save(any(Promotion.class)))
                .thenReturn(samplePromotion);
        PromotionResponseModel expectedResponse = PromotionResponseModel.builder()
                .promotionId(1L)
                .serviceId("service-123")
                .oldPrice(249.99f)
                .newPrice(199.99f)
                .discountMessage("Test Discount")
                .build();
        when(promotionMapper.entityToResponseModel(any(Promotion.class)))
                .thenReturn(expectedResponse);

        // Act
        PromotionResponseModel result = promotionService.createPromotion(sampleRequest);

        // Assert
        verify(serviceRepository).findByServiceIdentifier_ServiceId("service-123");
        verify(promotionRepository).save(any(Promotion.class));
        verify(promotionMapper).entityToResponseModel(any(Promotion.class));
        assertNotNull(result);
        assertEquals(1L, result.getPromotionId());
        assertEquals("service-123", result.getServiceId());
        assertEquals(249.99f, result.getOldPrice());
        assertEquals(199.99f, result.getNewPrice());
        assertEquals("Test Discount", result.getDiscountMessage());
    }

    @Test
    void createPromotion_serviceNotFound_throwsRuntimeException() {
        // Arrange
        when(serviceRepository.findByServiceIdentifier_ServiceId("service-123"))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                promotionService.createPromotion(sampleRequest)
        );
        assertEquals("Service not found with id: service-123", exception.getMessage());
        verify(promotionRepository, never()).save(any(Promotion.class));
        verify(promotionMapper, never()).entityToResponseModel(any(Promotion.class));
    }

    @Test
    void getAllPromotions_returnsMappedResponses() {
        // Arrange
        Promotion p1 = Promotion.builder()
                .id(1L)
                .service(sampleService)
                .oldPrice(249.99f)
                .newPrice(199.99f)
                .discountMessage("Discount 1")
                .build();
        Promotion p2 = Promotion.builder()
                .id(2L)
                .service(sampleService)
                .oldPrice(100f)
                .newPrice(80f)
                .discountMessage("Discount 2")
                .build();

        when(promotionRepository.findAll()).thenReturn(List.of(p1, p2));

        PromotionResponseModel r1 = PromotionResponseModel.builder()
                .promotionId(1L)
                .serviceId("service-123")
                .oldPrice(249.99f)
                .newPrice(199.99f)
                .discountMessage("Discount 1")
                .build();
        PromotionResponseModel r2 = PromotionResponseModel.builder()
                .promotionId(2L)
                .serviceId("service-123")
                .oldPrice(100f)
                .newPrice(80f)
                .discountMessage("Discount 2")
                .build();

        when(promotionMapper.entityListToResponseModel(anyList()))
                .thenReturn(List.of(r1, r2));

        // Act
        List<PromotionResponseModel> results = promotionService.getAllPromotions();

        // Assert
        verify(promotionRepository).findAll();
        verify(promotionMapper).entityListToResponseModel(anyList());
        assertEquals(2, results.size());
        assertEquals(1L, results.get(0).getPromotionId());
        assertEquals(2L, results.get(1).getPromotionId());
    }
    @Test
    void deletePromotion_promotionFound_deletesSuccessfully() {
        // Arrange
        Long promotionId = 1L;
        when(promotionRepository.findById(promotionId)).thenReturn(Optional.of(samplePromotion));

        // Act
        promotionService.deletePromotion(String.valueOf(promotionId));

        // Assert
        verify(promotionRepository).findById(promotionId);
        verify(serviceRepository).save(samplePromotion.getService()); // Ensures service price reset
        verify(promotionRepository).deleteById(promotionId);
    }

    @Test
    void deletePromotion_promotionNotFound_throwsException() {
        // Arrange
        Long promotionId = 1L;
        when(promotionRepository.findById(promotionId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                promotionService.deletePromotion(String.valueOf(promotionId))
        );
        assertEquals("Promotion not found with id: 1", exception.getMessage());

        verify(promotionRepository).findById(promotionId);
        verify(promotionRepository, never()).deleteById(any());
        verify(serviceRepository, never()).save(any());
    }

}