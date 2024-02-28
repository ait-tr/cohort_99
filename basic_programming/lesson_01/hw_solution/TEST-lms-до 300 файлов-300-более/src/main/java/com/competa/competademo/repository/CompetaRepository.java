package com.competa.competademo.repository;

import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.UserProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetaRepository extends CrudRepository<Competa, Long> {
    long countByUserProfile(UserProfile userProfile);

    @Query("select с from Competa с where с.userProfile = ?1")
    List<Competa> findAllByUser(UserProfile userProfile);

}
