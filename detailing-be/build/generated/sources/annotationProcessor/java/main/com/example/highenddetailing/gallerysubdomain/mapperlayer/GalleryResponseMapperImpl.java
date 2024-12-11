package com.example.highenddetailing.gallerysubdomain.mapperlayer;

import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryResponseModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-11T10:06:36-0500",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class GalleryResponseMapperImpl implements GalleryResponseMapper {

    @Override
    public GalleryResponseModel entityToResponseModel(Gallery gallery) {
        if ( gallery == null ) {
            return null;
        }

        GalleryResponseModel.GalleryResponseModelBuilder galleryResponseModel = GalleryResponseModel.builder();

        galleryResponseModel.galleryId( gallery.getGalleryIdentifier().getGalleryId() );
        galleryResponseModel.description( gallery.getDescription() );
        galleryResponseModel.imageUrl( gallery.getImageUrl() );

        return galleryResponseModel.build();
    }

    @Override
    public List<GalleryResponseModel> entityListToResponseModel(List<Gallery> galleryList) {
        if ( galleryList == null ) {
            return null;
        }

        List<GalleryResponseModel> list = new ArrayList<GalleryResponseModel>( galleryList.size() );
        for ( Gallery gallery : galleryList ) {
            list.add( entityToResponseModel( gallery ) );
        }

        return list;
    }
}
