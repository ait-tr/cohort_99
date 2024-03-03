package com.competa.competademo.repository;

import com.competa.competademo.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {

    Optional<Profession> findById(Long id);

    boolean existsByNameIgnoreCase(String name);
}
