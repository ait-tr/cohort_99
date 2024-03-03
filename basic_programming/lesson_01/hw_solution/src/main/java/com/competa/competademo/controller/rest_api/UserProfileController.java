package com.competa.competademo.controller.rest_api;

import com.competa.competademo.dto.UserDto;
import com.competa.competademo.entity.UserProfile;
import com.competa.competademo.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/api/user-profile")
public class UserProfileController {

    UserProfileService userProfileService;

    @GetMapping("/{id}")
    public UserProfile getUserProfile(@PathVariable Long id) {
        return userProfileService.getUserProfile(id);
    }

    @PutMapping("/{id}")
    public UserProfile updateUserProfile(@PathVariable Long id, @RequestBody UserProfile userProfile) {
        return userProfileService.updateUserProfile(id, userProfile);
    }
}
