package com.competa.competademo.service.impl;

import com.competa.competademo.dto.JobTitleDto;
import com.competa.competademo.dto.NewJobTitleDto;
import com.competa.competademo.entity.JobTitle;
import com.competa.competademo.exceptions.RestApiException;
import com.competa.competademo.repository.JobTitleRepository;
import com.competa.competademo.service.JobTitleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class JobTitleServiceImpl implements JobTitleService {

    JobTitleRepository jobTitleRepository;

    @Override
    public JobTitleDto addJobTitle(NewJobTitleDto newJobTitle) {
        JobTitle jobTitle = new JobTitle();
        jobTitle.setName(newJobTitle.getName());
        if (!isJobTitleExistsByName(newJobTitle.getName())) {
            jobTitleRepository.save(jobTitle);
        }
        return JobTitleDto.from(jobTitle);
    }

    @Override
    public JobTitleDto updateJobTitle(long id, NewJobTitleDto updateJobTitle) {
        JobTitle jobTitle = getJobTitleOrElseThrow(id);
        jobTitle.setName(updateJobTitle.getName());
        if (!isJobTitleExistsByName(updateJobTitle.getName())) {
            jobTitleRepository.save(jobTitle);
        }
        return JobTitleDto.from(jobTitle);
    }

    @Override
    public List<JobTitleDto> getAllJobTitle() {
        List<JobTitle> jobTitles = jobTitleRepository.findAll();
        return JobTitleDto.from(jobTitles);
    }

    @Override
    public JobTitleDto getByIdJobTitle(long id) {
        JobTitle jobTitle = getJobTitleOrElseThrow(id);
        return JobTitleDto.from(jobTitle);
    }

    @Override
    public JobTitleDto deleteJobTitle(long id) {
        JobTitle jobTitle = getJobTitleOrElseThrow(id);
        jobTitleRepository.deleteById(id);
        return JobTitleDto.from(jobTitle);
    }

    /**
     * @param id long - primary key table
     * @return if the record is not found returns an exception with the message
     */
    public JobTitle getJobTitleOrElseThrow(long id) {
        return jobTitleRepository.findById(id)
                .orElseThrow(() -> new RestApiException(HttpStatus.NOT_FOUND,
                        "Title of job with id " + id + " not found"));
    }

    /**
     * @param name String - value field in table
     * @return if a record with this field value already exists, it returns true and an exception with a message,
     * if not, it returns false
     */
    public boolean isJobTitleExistsByName(String name) {
        if (jobTitleRepository.existsByNameIgnoreCase(name)) {
            throw new RestApiException(HttpStatus.CONFLICT,
                    "Job title with name " + name + " already exists");
        }
        return false;
    }

}
