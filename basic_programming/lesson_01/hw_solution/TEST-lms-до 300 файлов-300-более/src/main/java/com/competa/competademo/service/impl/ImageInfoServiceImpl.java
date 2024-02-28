package com.competa.competademo.service.impl;

import com.competa.competademo.entity.ImageInfo;
import com.competa.competademo.service.ImageInfoService;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author Konstantin Glazunov
 * created on 18-Jan-24 1
 */
@Service
public class ImageInfoServiceImpl implements ImageInfoService {
    @Override
    public String getFileName(ImageInfo imageInfo) {
        String imageUrl = imageInfo.getUrl();
        Path path = FileSystems.getDefault().getPath(imageUrl);
        return path.getFileName().toString();
    }
}
