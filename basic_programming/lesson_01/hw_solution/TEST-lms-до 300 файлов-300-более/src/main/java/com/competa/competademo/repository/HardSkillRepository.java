package com.competa.competademo.repository;

import com.competa.competademo.entity.HardSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author Oleg Karimov
 *
 */
public interface HardSkillRepository extends JpaRepository<HardSkill, Long> {
    Optional<HardSkill> findById (Long hardSkillId);
    boolean existsByNameIgnoreCase(String name);

}
