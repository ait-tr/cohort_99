package com.competa.competademo.repository;

import com.competa.competademo.entity.DriverLicence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverLicenceRepository extends JpaRepository<DriverLicence, Long> {

    Optional<DriverLicence> findById(Long id);

    boolean existsByNameIgnoreCase(String name);
}
