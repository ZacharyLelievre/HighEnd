package com.example.highenddetailing.promotionsubdomain.presentationlayer;

import com.example.highenddetailing.promotionsubdomain.businesslayer.PromotionService;
import com.example.highenddetailing.promotionsubdomain.datalayer.Promotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromotionControllerTest {

    @Mock
    private PromotionService promotionService;

    @InjectMocks
    private PromotionController promotionController;

    private PromotionResponseModel sampleResponse;

    @BeforeEach
    void setUp() {
        sampleResponse = PromotionResponseModel.builder()
                .promotionId(1L)
                .serviceId("service-123")
                .oldPrice(249.99f)
                .newPrice(199.99f)
                .discountMessage("Spring Discount")
                .build();
    }

    @Test
    void createPromotion_shouldReturn201_whenSuccessful() {
        // Arrange
        PromotionRequestModel request = new PromotionRequestModel("service-123", 199.99f, "Spring Discount");
        when(promotionService.createPromotion(request)).thenReturn(sampleResponse);

        // Act
        ResponseEntity<PromotionResponseModel> response = promotionController.createPromotion(request);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(sampleResponse, response.getBody());
        verify(promotionService, times(1)).createPromotion(request);
    }

    @Test
    void getAllPromotions_shouldReturnList() {
        // Arrange
        when(promotionService.getAllPromotions()).thenReturn(List.of(sampleResponse));

        // Act
        ResponseEntity<List<PromotionResponseModel>> response = promotionController.getAllPromotions();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(promotionService, times(1)).getAllPromotions();
    }

    @Test
    void deletePromotion_shouldCallService() {
        // Arrange
        String promotionId = "1";

        // Act
        promotionController.deletePromotion(promotionId);

        // Assert
        verify(promotionService, times(1)).deletePromotion(promotionId);
    }
}
