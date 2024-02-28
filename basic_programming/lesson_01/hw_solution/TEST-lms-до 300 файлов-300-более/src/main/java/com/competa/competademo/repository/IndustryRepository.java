package com.competa.competademo.repository;

import com.competa.competademo.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndustryRepository extends JpaRepository<Industry, Long> {
    Optional<Industry> findById(Long id);

    boolean existsByNameIgnoreCase(String name);

}
