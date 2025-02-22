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
}