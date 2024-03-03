package com.competa.competademo.dto;

import com.competa.competademo.entity.ImageInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Konstantin Glazunov
 * created on 23-Jan-24 1
 */
@DisplayName("ImageInfoDto is works: ")
class ImageInfoDtoTests {

    @Test
    public void testFromImageInfoDto() {

        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setName("testImageInfo.name");
        imageInfo.setUrl("/imageInfoTetst.url");

        ImageInfoDto imageInfoDto = ImageInfoDto.from(imageInfo);

        assertEquals(imageInfoDto.getName(), imageInfo.getName());
        assertEquals(imageInfoDto.getUrl(), imageInfo.getUrl());
        assertEquals(imageInfoDto.getId(), imageInfo.getId());
    }
}
