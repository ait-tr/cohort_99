package com.competa.competademo.service.impl;

import com.competa.competademo.entity.User;
import com.competa.competademo.entity.UserProfile;
import com.competa.competademo.repository.UserProfileRepository;
import com.competa.competademo.service.UserProfileService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    @Transactional
    public UserProfile createUserProfileForUser(User user) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        return userProfileRepository.save(userProfile);
    }
    @Override
    public UserProfile getUserProfile(Long id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile with ID " + id + " not found."));
    }

    @Override
    public UserProfile updateUserProfile(final Long id, UserProfile updatedProfile) {
        final UserProfile profile = getUserProfile(id);

        profile.setLastName(updatedProfile.getLastName());
        profile.setPhone(updatedProfile.getPhone());
        profile.setDateOfBirth(updatedProfile.getDateOfBirth());
        profile.setProfession(updatedProfile.getProfession());
        profile.setLevel(updatedProfile.getLevel());
        profile.setPublic(updatedProfile.isPublic());
        profile.setResidence(updatedProfile.getResidence());
        profile.setReadyToMove(updatedProfile.isReadyToMove());

        return userProfileRepository.save(profile);
    }
    @Override
    public void deleteUserProfile(Long id) {
        userProfileRepository.delete(getUserProfile(id));
    }
}
