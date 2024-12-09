package com.example.highenddetailing.gallerysubdomain.businesslayer;

import com.example.highenddetailing.gallerysubdomain.datalayer.Gallery;
import com.example.highenddetailing.gallerysubdomain.datalayer.GalleryRepository;
import com.example.highenddetailing.gallerysubdomain.domainclientlayer.GalleryResponseModel;
import com.example.highenddetailing.gallerysubdomain.mapperlayer.GalleryResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final GalleryResponseMapper galleryResponseMapper;
    @Override
    public List<GalleryResponseModel> getAllGalleries() {
        return galleryResponseMapper.entityListToResponseModel(galleryRepository.findAll());
    }
}
