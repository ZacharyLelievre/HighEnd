package com.example.highenddetailing.gallerysubdomain.datalayer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Table(name = "galleries")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private GalleryIdentifier galleryIdentifier;
    private String description;
    private String imageUrl;

    public Gallery(GalleryIdentifier galleryIdentifier, String description, String imageUrl) {
        this.galleryIdentifier = galleryIdentifier;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
