package com.example.highenddetailing.promotionsubdomain.presentationlayer;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionRequestModel {
    private String serviceId;       // which service to discount
    private float newPrice;         // new promotional price
    private String discountMessage; // e.g. "Spring Special! 20% off"
}
