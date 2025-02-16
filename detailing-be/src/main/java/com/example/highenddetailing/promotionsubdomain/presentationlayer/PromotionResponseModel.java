package com.example.highenddetailing.promotionsubdomain.presentationlayer;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionResponseModel {
    private Long promotionId;
    private String serviceId;
    private float oldPrice;
    private float newPrice;
    private String discountMessage;
}
