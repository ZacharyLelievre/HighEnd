package com.example.highenddetailing.servicessubdomain.domainclientlayer;

import com.example.highenddetailing.servicessubdomain.businesslayer.ServiceService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponseModel> createService(
            @RequestParam("serviceName") String serviceName,
            @RequestParam("timeRequired") String timeRequired,
            @RequestParam("price") float price,
            @RequestPart("image") MultipartFile imageFile
    ) {
        try {
            String originalFilename = imageFile.getOriginalFilename();
            if (originalFilename == null) {
                originalFilename = "unknown";
            }
            String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;

            Path uploadsDir = Paths.get("uploads").toAbsolutePath();
            Files.createDirectories(uploadsDir);

            Path targetPath = uploadsDir.resolve(uniqueFilename);
            Files.copy(imageFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            String imageUrl = "https://high-end-detailing.com/api/services/images/" + uniqueFilename;

            ServiceResponseModel newService = serviceService.createService(
                    serviceName,
                    timeRequired,
                    price,
                    imageUrl
            );

            return ResponseEntity.ok(newService);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads").resolve(filename).toAbsolutePath();
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes = Files.readAllBytes(filePath);
            ByteArrayResource resource = new ByteArrayResource(imageBytes);

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

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable String serviceId) {
        if (serviceService.getServiceById(serviceId).isPresent()) {
            serviceService.deleteService(serviceId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // NEW: Update (PUT) endpoint for editing a service
    @PutMapping(value = "/{serviceId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponseModel> updateService(
            @PathVariable String serviceId,
            @RequestParam("serviceName") String serviceName,
            @RequestParam("timeRequired") String timeRequired,
            @RequestParam("price") float price,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            String imageUrl = null;
            if (imageFile != null && !imageFile.isEmpty()) {
                String originalFilename = Optional.ofNullable(imageFile.getOriginalFilename())
                        .orElse("unknown");
                String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;

                Path uploadsDir = Paths.get("uploads").toAbsolutePath();
                Files.createDirectories(uploadsDir);

                Path targetPath = uploadsDir.resolve(uniqueFilename);
                Files.copy(imageFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                imageUrl = "https://high-end-detailing.com/api/services/images/" + uniqueFilename;
            }

            // If no new image was uploaded, preserve the old one
            if (imageUrl == null) {
                ServiceResponseModel oldService = serviceService.getServiceById(serviceId)
                        .orElseThrow(() -> new RuntimeException("Service not found"));
                imageUrl = oldService.getImagePath();
            }

            ServiceResponseModel updated = serviceService.updateService(
                    serviceId, serviceName, timeRequired, price, imageUrl
            );

            return ResponseEntity.ok(updated);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}