package com.example.highenddetailing.gallerysubdomain.datalayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class GalleryIdentifier {

    private String galleryId;

    public GalleryIdentifier() {
        this.galleryId = UUID.randomUUID().toString();
    }
}
