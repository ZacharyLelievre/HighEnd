package com.example.highenddetailing.gallerysubdomain.mapperlayer;

import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GalleryResponseMapper {

    @Mapping(expression = "java(gallery.getGalleryIdentifier().getGalleryId())", target = "galleryId")
    @Mapping(expression = "java(gallery.getDescription())", target = "description")
    @Mapping(expression = "java(gallery.getImageUrl())", target = "imageUrl")

    GalleryResponseModel entityToResponseModel(Gallery gallery);
    List<GalleryResponseModel> entityListToResponseModelList(List<Gallery> galleryList);
}
