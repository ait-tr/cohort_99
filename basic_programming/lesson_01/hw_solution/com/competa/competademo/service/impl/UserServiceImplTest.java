package com.competa.competademo.service.impl;

import com.competa.competademo.dto.CreateUserDto;
import com.competa.competademo.dto.UserDto;
import com.competa.competademo.entity.User;
import com.competa.competademo.entity.UserStatus;
import com.competa.competademo.mail.MailSenderService;
import com.competa.competademo.repository.UserRepository;
import com.competa.competademo.service.ConfirmationCodeService;
import com.competa.competademo.service.FilesStorageService;
import com.competa.competademo.service.RoleService;
import com.competa.competademo.service.UserProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;
    @Mock
    private FilesStorageService filesStorageService;
    @Mock
    private MailSenderService mailSenderService;
    @Mock
    private UserProfileService userProfileService;
    @Mock
    private ConfirmationCodeService confirmationCodeService;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    CreateUserDto userDto;

    @Test
    void test_saveUser_uslovija() {
        String email = "user-1@gmail.com";
        String pass = "user-1-pass";
        String hashedPass = "user-1-pass-hashedPass";

        when(userDto.getEmail()).thenReturn(email);
        when(userDto.getEmail()).thenReturn(pass);

        User user = new User();
        user.setEmail(email);
        user.setPassword(pass);
        when(userDto.toEntity()).thenReturn(user);

        when(passwordEncoder.encode(anyString())).thenReturn(hashedPass);

        User savedNewUser = new User(2L, "vp", hashedPass, email, UserStatus.NOT_CONFIRMED, null, null, null);
        when(userRepository.save(any(User.class))).thenReturn(savedNewUser);


        UserDto savedUser = userService.saveUser(userDto);

        verify(userRepository).save(argThat(
                userSaved -> userSaved.getEmail().equals(email) &&
                        userSaved.getPassword().equals("hashedPass") &&
                        userSaved.getNickName().equals("vp")
        ));
        verify(mailSenderService).sendMail(eq(email), eq("Registration"), anyString());

        when(userService.saveUser(user)).thenReturn(UserDto.from(user,UserStatus.NOT_CONFIRMED).toEntity());

    }
}