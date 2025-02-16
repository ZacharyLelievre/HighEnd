package com.example.highenddetailing.gallerysubdomain.domainclientlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GalleryRequestModel {

    private String description;
    private String imageUrl;
}
