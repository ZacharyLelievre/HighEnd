package com.example.highenddetailing.gallerysubdomain.utils;

import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryResponseModel;
import org.springframework.beans.BeanUtils;

public class EntityModelUtil {

    public static GalleryResponseModel toGalleryResponseModel(Gallery gallery){
        GalleryResponseModel galleryResponseModel = new GalleryResponseModel();
        BeanUtils.copyProperties(gallery, galleryResponseModel);
        return galleryResponseModel;
    }
}
