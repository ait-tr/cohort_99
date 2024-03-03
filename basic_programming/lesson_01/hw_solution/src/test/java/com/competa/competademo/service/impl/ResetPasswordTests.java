package com.competa.competademo.service.impl;

import com.competa.competademo.dto.PassChangeRequestDto;
import com.competa.competademo.entity.User;
import com.competa.competademo.exceptions.EqualPasswordsExeption;
import com.competa.competademo.exceptions.IncorrectPasswordExeption;
import com.competa.competademo.mail.MailSenderService;
import com.competa.competademo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Konstantin Glazunov
 * created on 15-Jan-24 1
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ResetPassword is works: ")
public class ResetPasswordTests {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MailSenderService mailSenderService;

    @Test
    void resetCorrectPassword() {
        User authUser = new User();
        String authUserPassword = "oldPassword007!";
        authUser.setEmail("otto.shtirliz@competa.test");
        authUser.setPassword(authUserPassword);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(authUser.getEmail());

        PassChangeRequestDto passChangeRequestDto =
                new PassChangeRequestDto(authUser.getPassword(), "newPassword123!");

        when(userRepository.findByEmail(authUser.getEmail())).thenReturn(Optional.of(authUser));
        when(passwordEncoder.encode(passChangeRequestDto.newPassword())).thenReturn("hashedNewPassword");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        userService.resetPassword(passChangeRequestDto);

        assertEquals("hashedNewPassword", authUser.getPassword());
        verify(securityContext, times(1)).getAuthentication();
        verify(authentication, times(1)).getName();
        verify(userRepository, times(1)).findByEmail(eq(authUser.getEmail()));
        verify(passwordEncoder, times(1)).matches(eq(passChangeRequestDto.oldPassword()),
                eq("oldPassword007!"));
        verify(passwordEncoder, times(1)).encode(eq(passChangeRequestDto.newPassword()));
        verify(userRepository, times(1)).save(
                argThat(arg -> arg.getEmail().equals(authUser.getEmail()) &&
                        arg.getPassword().equals("hashedNewPassword"))
        );
        verify(mailSenderService, times(1)).sendMail(eq(authUser.getEmail()),
                eq("COMPETA security system message"),
                eq("Your password has been changed")
        );
    }

    @Test
    void resetIncorrectPassword() {
        User authUser = new User();
        String authUserPassword = "oldPassword007!";
        authUser.setEmail("otto.shtirliz@competa.test");
        authUser.setPassword(authUserPassword);

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateTime = localDateTime.format(formatter);


        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(authUser.getEmail());

        PassChangeRequestDto passChangeRequestDto =
                new PassChangeRequestDto("IncorrectPassword", "newPassword123!");

        when(userRepository.findByEmail(authUser.getEmail())).thenReturn(Optional.of(authUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThatThrownBy(() -> userService.resetPassword(passChangeRequestDto))
                .isInstanceOf(IncorrectPasswordExeption.class)
                .hasMessage("Incorrect old password");

        verify(mailSenderService, times(1)).sendMail(eq(authUser.getEmail()),
                eq("COMPETA security system message"),
                contains("A password reset attempt has been detected at " + dateTime)
        );
    }

    @Test
    void resetSameNewPassword() {
        User authUser = new User();
        String authUserPassword = "oldPassword007!";
        authUser.setEmail("otto.shtirliz@competa.test");
        authUser.setPassword(authUserPassword);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(authUser.getEmail());

        PassChangeRequestDto passChangeRequestDto =
                new PassChangeRequestDto(authUser.getPassword(), "oldPassword007!");

        when(userRepository.findByEmail(authUser.getEmail())).thenReturn(Optional.of(authUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        assertThatThrownBy(() -> userService.resetPassword(passChangeRequestDto))
                .isInstanceOf(EqualPasswordsExeption.class)
                .hasMessage("New password should be different from the old password");
    }
}
