package com.competa.competademo.controller.rest_api;

import com.competa.competademo.controller.rest_api.api.UserAuthControllerApi;
import com.competa.competademo.dto.NewUserDto;
import com.competa.competademo.dto.UserDto;
import com.competa.competademo.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Oleg Karimov
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
public class UserAuthController implements UserAuthControllerApi {

    UserService userService;

    @Override
    public UserDto registerUser(NewUserDto createUserDto) {
        return userService.saveUser(createUserDto);
    }
}
