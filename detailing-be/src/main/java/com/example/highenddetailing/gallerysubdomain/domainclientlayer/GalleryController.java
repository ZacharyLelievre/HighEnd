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
@RequiredArgsConstructor
@RequestMapping("/api/galleries")
@CrossOrigin(origins = "http://localhost:3000")
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<GalleryResponseModel>> getAllGalleries(){
        return ResponseEntity.ok().body(galleryService.getAllGalleries());
    }
}