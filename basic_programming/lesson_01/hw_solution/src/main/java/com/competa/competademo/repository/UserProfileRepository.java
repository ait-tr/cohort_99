package com.competa.competademo.repository;

import com.competa.competademo.entity.User;
import com.competa.competademo.entity.UserProfile;
import com.competa.competademo.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    /**
     * Метод для поиска профиля по идентификатору
     *
     * @param id must not be {@literal null}. Идентификатор профиля
     * @return Optional с профилем, если найден, иначе пустой Optional
     */
    Optional<UserProfile> findById(Long id);

    /**
     * Удаляет профиль по указанному идентификатору
     *
     * @param id must not be {@literal null}. Идентификатор профиля, который требуется удалить
     */
    void deleteById(Long id);

    default UserProfile getAuthUserProfile(UserService userService) {
        User user = userService.getAuthUser();
        return user.getUserProfile();
    }

}
