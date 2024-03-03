package com.competa.competademo.service;
import com.competa.competademo.entity.User;
import com.competa.competademo.entity.UserProfile;
/**
 * @author Konstantin Glazunov
 * created on 22-Jan-24 1
 */
public interface UserProfileService {

    public UserProfile createUserProfileForUser(User user);

    public UserProfile getUserProfile(Long id);

    public UserProfile updateUserProfile(final Long id, UserProfile updatedProfile);

    public void deleteUserProfile(Long id);
}
