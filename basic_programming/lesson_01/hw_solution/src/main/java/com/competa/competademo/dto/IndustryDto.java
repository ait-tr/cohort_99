package com.competa.competademo.dto;

import com.competa.competademo.entity.Industry;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Industry")
public class IndustryDto{

    @Schema(description = "Industry ID", example = "1")
    private Long id;

    @Schema(description = "Industry", example = "education")
    private String name;

    public static IndustryDto from(Industry industry) {
        return IndustryDto.builder()
                .id(industry.getId())
                .name(industry.getName())
                .build();
    }

    public static List<IndustryDto> from(List<Industry> industries) {
        return industries.stream()
                .map(IndustryDto::from)
                .collect(Collectors.toList());
    }

    public Industry toEntity(){
        return new Industry(id, name);
    }
}
