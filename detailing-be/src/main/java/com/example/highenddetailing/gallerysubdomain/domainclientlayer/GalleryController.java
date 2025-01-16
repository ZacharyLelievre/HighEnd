package com.example.highenddetailing.gallerysubdomain.domainclientlayer;

import com.example.highenddetailing.gallerysubdomain.businesslayer.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/galleries")
@CrossOrigin(origins = "https://highend-1.onrender.com")

public class GalleryController {

    private final GalleryService galleryService;

    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<GalleryResponseModel>> getAllGalleries(){
        return ResponseEntity.ok().body(galleryService.getAllGalleries());
    }
}
