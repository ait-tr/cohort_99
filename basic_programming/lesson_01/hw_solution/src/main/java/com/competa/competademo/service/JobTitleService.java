package com.competa.competademo.service;

import com.competa.competademo.dto.JobTitleDto;
import com.competa.competademo.dto.NewJobTitleDto;

import java.util.List;

public interface JobTitleService {
    JobTitleDto addJobTitle(NewJobTitleDto newJobTitle);

    JobTitleDto updateJobTitle(long id, NewJobTitleDto updateJobTitle);

    List<JobTitleDto> getAllJobTitle();

    JobTitleDto getByIdJobTitle(long id);

    JobTitleDto deleteJobTitle(long id);
}
