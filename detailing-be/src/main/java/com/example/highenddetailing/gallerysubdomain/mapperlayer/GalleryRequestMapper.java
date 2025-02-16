package com.example.highenddetailing.gallerysubdomain.mapperlayer;

import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryRequestModel;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GalleryRequestMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
    })
    Gallery toGallery(GalleryRequestModel dto);

    GalleryResponseModel toGalleryResponseDto(Gallery gallery);
}
