package com.example.highenddetailing.gallerysubdomain.domainclientlayer;

import com.example.highenddetailing.gallerysubdomain.businesslayer.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description) {
        try {
            String message = galleryService.uploadImage(file, description);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            // Get the image bytes from the service
            byte[] imageBytes = galleryService.getImage(filename);

            // Determine the file extension and set appropriate content type
            String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            MediaType mediaType = getMediaTypeForExtension(extension);

            // Wrap the bytes in a ByteArrayResource and return
            ByteArrayResource resource = new ByteArrayResource(imageBytes);

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private MediaType getMediaTypeForExtension(String extension) {
        switch (extension) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

}
