package com.competa.competademo.repository;

import com.competa.competademo.entity.SoftSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author Oleg Karimov
 *
 */
public interface SoftSkillRepository extends JpaRepository<SoftSkill, Long> {
    Optional<SoftSkill> findById (Long softSkillId);

    boolean existsByNameIgnoreCase(String name);

}
