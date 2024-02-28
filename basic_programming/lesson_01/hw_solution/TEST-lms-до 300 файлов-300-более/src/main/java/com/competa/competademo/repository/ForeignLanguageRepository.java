package com.competa.competademo.repository;

import com.competa.competademo.entity.ForeignLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForeignLanguageRepository extends JpaRepository<ForeignLanguage,Long> {
    Optional<ForeignLanguage> findById(Long id);
    List<ForeignLanguage> findByName(String name);
    boolean existsByNameIgnoreCase(String name);
}
