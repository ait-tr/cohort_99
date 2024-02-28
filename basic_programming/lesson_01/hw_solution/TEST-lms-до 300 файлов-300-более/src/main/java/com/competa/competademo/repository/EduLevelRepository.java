package com.competa.competademo.repository;

import com.competa.competademo.entity.EduLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EduLevelRepository extends JpaRepository<EduLevel, Long> {

    Optional<EduLevel> findById(Long id);
    boolean existsByNameIgnoreCase(String name);
    boolean findByCompetaId(Long id);
}
