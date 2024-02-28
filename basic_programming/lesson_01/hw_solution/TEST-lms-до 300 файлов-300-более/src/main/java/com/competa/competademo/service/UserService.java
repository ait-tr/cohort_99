package com.competa.competademo.service;

import com.competa.competademo.dto.CreateUserDto;
import com.competa.competademo.dto.NewUserDto;
import com.competa.competademo.dto.PassChangeRequestDto;
import com.competa.competademo.dto.UserDto;
import com.competa.competademo.entity.User;
import com.competa.competademo.entity.UserStatus;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface UserService {


    UserDto saveUser(CreateUserDto userDto);

    User addUserRole(long userId, String roleName);

    User removeUserRole(long userId, String roleName);

    boolean isUserByEmailExist(String email);

    User findById(long id);

    List<UserDto> findAllUsers();

    User saveUser(User user);

    User findByEmail(String userEmail);

    User getAuthUser();

    UserDto getUser(Long userId);

    // TODO to remove
    List<UserDto> getUsersList(List<User> users, List<String> avatarImageDataList);

    User updateUserStatus(@NonNull Long userId,
                          @NonNull UserStatus userStatus);

    UserDto saveUser(NewUserDto createUserDto);

    void resetPassword(@RequestBody @Valid PassChangeRequestDto passChangeRequestDto);

    UserDto confirm(String code);
}
