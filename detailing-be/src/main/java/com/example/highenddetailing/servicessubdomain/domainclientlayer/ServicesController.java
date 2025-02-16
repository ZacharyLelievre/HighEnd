package com.example.highenddetailing.servicessubdomain.domainclientlayer;

import com.example.highenddetailing.servicessubdomain.businesslayer.ServiceService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class ServicesController {

    private final ServiceService serviceService;

    public ServicesController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ServiceResponseModel>> getAllServices() {
        return ResponseEntity.ok().body(serviceService.getAllServices());
    }

    @GetMapping(value = "/{serviceId}", produces = "application/json")
    public ResponseEntity<ServiceResponseModel> getServiceById(@PathVariable String serviceId) {
        return serviceService.getServiceById(serviceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- CREATE SERVICE WITH IMAGE UPLOAD ---
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponseModel> createService(
            @RequestParam("serviceName") String serviceName,
            @RequestParam("timeRequired") String timeRequired,
            @RequestParam("price") float price,
            @RequestPart("image") MultipartFile imageFile
    ) {
        try {
            // Optional: Validate content type if you only want PNG
            // if (!"image/png".equalsIgnoreCase(imageFile.getContentType())) {
            //     return ResponseEntity.badRequest().body(null);
            // }

            // Generate a unique filename
            String originalFilename = imageFile.getOriginalFilename();
            if (originalFilename == null) {
                originalFilename = "unknown";
            }
            String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;

            // Ensure uploads folder exists
            Path uploadsDir = Paths.get("uploads").toAbsolutePath();
            Files.createDirectories(uploadsDir);

            // Copy file to uploads folder
            Path targetPath = uploadsDir.resolve(uniqueFilename);
            Files.copy(imageFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Build the accessible URL for retrieving the image
            // e.g., /api/services/images/{uniqueFilename}
            String imageUrl = "api/services/images/" + uniqueFilename;

            // Create and save the service
            ServiceResponseModel newService = serviceService.createService(
                    serviceName,
                    timeRequired,
                    price,
                    imageUrl
            );

            return ResponseEntity.ok(newService);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // --- SERVE IMAGE BACK TO CLIENT ---
    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads").resolve(filename).toAbsolutePath();
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            // Read file bytes
            byte[] imageBytes = Files.readAllBytes(filePath);
            ByteArrayResource resource = new ByteArrayResource(imageBytes);

            // Determine media type
            MediaType mediaType = getMediaTypeForExtension(getFileExtension(filename));

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
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