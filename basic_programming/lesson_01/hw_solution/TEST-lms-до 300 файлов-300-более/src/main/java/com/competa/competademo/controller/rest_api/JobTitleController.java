package com.competa.competademo.controller.rest_api;

import com.competa.competademo.controller.rest_api.api.JobTitleApi;
import com.competa.competademo.dto.JobTitleDto;
import com.competa.competademo.dto.NewJobTitleDto;
import com.competa.competademo.service.JobTitleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
public class JobTitleController implements JobTitleApi {

    JobTitleService jobTitleService;

    @Override
    public JobTitleDto addJobTitle(NewJobTitleDto newJobTitle) {
        return jobTitleService.addJobTitle(newJobTitle);
    }

    @Override
    public JobTitleDto updateJobTitle(long id, NewJobTitleDto updateJobTitle) {
        return jobTitleService.updateJobTitle(id, updateJobTitle);
    }

    @Override
    public List<JobTitleDto> getAllJobTitle() {
        return jobTitleService.getAllJobTitle();
    }

    @Override
    public JobTitleDto deleteJobTitle(long id) {
        return jobTitleService.deleteJobTitle(id);
    }
}
