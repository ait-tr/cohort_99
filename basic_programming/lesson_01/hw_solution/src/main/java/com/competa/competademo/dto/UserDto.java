package com.competa.competademo.dto;

import com.competa.competademo.entity.Role;
import com.competa.competademo.entity.User;
import com.competa.competademo.entity.UserProfile;
import com.competa.competademo.entity.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User Dto")
public class UserDto {

    @Schema(description = "User id", example = "1")
    protected Long id;

    @NotBlank
    protected String nickName;

    @Schema(description = "User first name", example = "Vasja")
    protected String firstName;

    @Schema(description = "User last name", example = "Pupkin")
    protected String lastName;

    @NotBlank(message = "Email should not be empty")
    @Email
    @Schema(description = "User email", example = "vasja.pupkin@competa.test")
    protected String email;

    @Schema(description = "User roles", example = "ROLE_USER")
    protected Set<String> roles = new HashSet<>();

    @Schema(description = "Avatar image name", example = "Дядя_Фёдор.jpg")
    protected String avatarName;

    @Schema(description = "Avatar image in Base64", example = "imageData: data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAA...")
    protected String avatarImageData;

    @Schema(description = "User status", example = "CONFIRMED")
    protected UserStatus userStatus;

    public UserDto(final User user) {
        UserProfile userProfile = user.getUserProfile();
        if (Objects.nonNull(userProfile)) {
            this.firstName = userProfile.getFirstName();
            this.lastName = userProfile.getLastName();
        }
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
    }

    public static UserDto from(User user,UserStatus userStatus) {
        return UserDto.builder()
                .nickName(user.getNickName())
                .email(user.getEmail())
                .userStatus(userStatus)
                .build();
    }

    public static UserDto from(User user) {
        return new UserDto(user);
    }

    public User toEntity() {
        return User.builder()
                .nickName(this.nickName)
                .email(this.email)
                .id(this.getId())
                .build();
    }

    public static UserDto from(User user, String avatarImageData) {
        final UserDto userDto = from(user);
        final UserProfile userProfile = user.getUserProfile();
        if (Objects.nonNull(userProfile) && Objects.nonNull(userProfile.getProfileAvatar())) {
            userDto.setAvatarName(userProfile.getProfileAvatar().getName());
        }
        userDto.setAvatarImageData(avatarImageData);
        return userDto;
    }
}
