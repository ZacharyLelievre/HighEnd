package com.example.highenddetailing.gallerysubdomain.businesslayer;

import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryResponseModel;

import java.util.List;

public interface GalleryService {

    List<GalleryResponseModel> getAllGalleries();
    void deleteImage(String galleryId);
}
