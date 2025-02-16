package com.example.highenddetailing.promotionsubdomain.presentationlayer;

import com.example.highenddetailing.promotionsubdomain.businesslayer.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<PromotionResponseModel> createPromotion(@RequestBody PromotionRequestModel request) {
        PromotionResponseModel created = promotionService.createPromotion(request);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<PromotionResponseModel>> getAllPromotions() {
        return ResponseEntity.ok(promotionService.getAllPromotions());
    }
    @DeleteMapping("/{promotionId}")
    public ResponseEntity<Void> deletePromotion(@PathVariable String promotionId) {
        promotionService.deletePromotion(promotionId);
        return ResponseEntity.noContent().build();
    }

}
