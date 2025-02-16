package com.example.highenddetailing.gallerysubdomain.domainclientlayer;

import com.example.highenddetailing.gallerysubdomain.businesslayer.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/galleries")
public class GalleryController {

    private final GalleryService galleryService;

    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<GalleryResponseModel>> getAllGalleries() {
        return ResponseEntity.ok().body(galleryService.getAllGalleries());
    }

    @DeleteMapping("/{galleryId}")
    public ResponseEntity<Void> deleteGallery(@PathVariable String galleryId) {
        try {
            galleryService.deleteImage(galleryId);
            return  ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
