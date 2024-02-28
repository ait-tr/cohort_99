package com.competa.competademo.repository;

import com.competa.competademo.entity.ForeignLanguageLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForeignLanguageLevelRepository extends JpaRepository<ForeignLanguageLevel,Long> {

    Optional<ForeignLanguageLevel> findById(Long id);
    List<ForeignLanguageLevel> findByName(String name);
    boolean existsByNameIgnoreCase(String name);
}
