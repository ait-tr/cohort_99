package com.competa.competademo.dto;

import com.competa.competademo.entity.ImageInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Konstantin Glazunov
 * created on 23-Jan-24 1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ImageInfoDto")
public class ImageInfoDto {
    @Schema(description = "ImageInfo Id", example = "1")
    private Long id;


    @Schema(description = "Image name Id", example = "MyAvatar.jpg")
    private String name;

    @Schema(description = "Image Url", example = ".\\uploads\\94f44b47-3603-4d44-bc05-e11862281100.jpg")
    private String url;
    public static ImageInfoDto from(ImageInfo imageInfo) {
        return ImageInfoDto.builder()
                .id(imageInfo.getId())
                .name(imageInfo.getName())
                .url(imageInfo.getUrl())
                .build();
    }
}
