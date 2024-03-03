package com.competa.competademo.dto;

import com.competa.competademo.entity.JobTitle;
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
@Schema(description = "Job title")
public class JobTitleDto {

    @Schema(description = "Job title ID", example = "1")
    private Long id;

    @Schema(description = "Title of JobTitle", example = "инженер-конструктор")
    private String name;

    public static JobTitleDto from(JobTitle jobTitle) {
        return JobTitleDto.builder()
                .id(jobTitle.getId())
                .name(jobTitle.getName())
                .build();
    }

    public static List<JobTitleDto> from(List<JobTitle> jobTitles) {
        return jobTitles.stream()
                .map(JobTitleDto::from)
                .collect(Collectors.toList());
    }

}
