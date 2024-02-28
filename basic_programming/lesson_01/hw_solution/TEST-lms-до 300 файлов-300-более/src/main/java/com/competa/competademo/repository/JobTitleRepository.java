package com.competa.competademo.repository;

import com.competa.competademo.entity.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {

    Optional<JobTitle> findById(Long id);

    boolean existsByNameIgnoreCase(String name);
}
