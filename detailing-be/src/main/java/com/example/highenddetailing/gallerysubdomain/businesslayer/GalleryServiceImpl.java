package com.example.highenddetailing.gallerysubdomain.businesslayer;

import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.datalayer.GalleryIdentifier;
import com.example.highenddetailing.gallerysubdomain.datalayer.GalleryRepository;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryResponseModel;
import com.example.highenddetailing.gallerysubdomain.mapperlayer.GalleryResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final GalleryResponseMapper galleryResponseMapper;
    private final String uploadDir = "uploads/";



    public GalleryServiceImpl(GalleryRepository galleryRepository, GalleryResponseMapper galleryResponseMapper) {
        this.galleryRepository = galleryRepository;
        this.galleryResponseMapper = galleryResponseMapper;
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Override
    public List<GalleryResponseModel> getAllGalleries() {
        return galleryResponseMapper.entityListToResponseModel(galleryRepository.findAll());
    }

    @Override
    public void deleteImage(String galleryId) {
        Gallery gallery = galleryRepository
                .findByGalleryIdentifier_GalleryId(galleryId)
                .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + galleryId));
        galleryRepository.delete(gallery);

    }

    @Override
    public String uploadImage(MultipartFile file, String description) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File destinationFile = new File(uploadDir + fileName);
            Files.copy(file.getInputStream(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Save image details in DB
            Gallery gallery = Gallery.builder()
                    .galleryIdentifier(new GalleryIdentifier())
                    .description(description)
                    .imageUrl("https://high-end-detailing.com/images/" + fileName)
                    .build();

            galleryRepository.save(gallery);
            return "Image uploaded successfully";
        } catch (IOException e) {
            throw new RuntimeException("Error uploading image", e);
        }
    }

    @Override
    public byte[] getImage(String filename) {
        try {
            File file = new File(uploadDir + filename);
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Image not found", e);
        }
    }
}