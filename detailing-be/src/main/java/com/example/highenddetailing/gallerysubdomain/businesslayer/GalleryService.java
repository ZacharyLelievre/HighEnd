package com.example.highenddetailing.gallerysubdomain.businesslayer;

import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryResponseModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GalleryService {

    List<GalleryResponseModel> getAllGalleries();
    void deleteImage(String galleryId);
    String uploadImage(MultipartFile file, String description);
    byte[] getImage(String filename);
}
