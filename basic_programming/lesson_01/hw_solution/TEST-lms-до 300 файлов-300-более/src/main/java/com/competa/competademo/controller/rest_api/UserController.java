package com.competa.competademo.controller.rest_api;

import com.competa.competademo.controller.rest_api.api.UserApi;
import com.competa.competademo.dto.PassChangeRequestDto;
import com.competa.competademo.dto.UserDto;
import com.competa.competademo.security.details.AuthenticatedUser;
import com.competa.competademo.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author Oleg Karimov
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    UserService userService;

    @Override
    public UserDto getMyProfile(AuthenticatedUser currentUser) {
        return userService.getUser(currentUser.id());
    }

    @Override
    public void resetPassword(@RequestBody @Valid PassChangeRequestDto passChangeRequestDto) {
        userService.resetPassword(passChangeRequestDto);
    }

    @Override
    public UserDto getConfirmation(String code) {
      return userService.confirm(code);
    }
}
