package com.competa.competademo.service.impl;

import com.competa.competademo.dto.CreateUserDto;
import com.competa.competademo.dto.NewUserDto;
import com.competa.competademo.dto.PassChangeRequestDto;
import com.competa.competademo.dto.UserDto;
import com.competa.competademo.entity.*;
import com.competa.competademo.exceptions.*;
import com.competa.competademo.mail.MailSenderService;
import com.competa.competademo.repository.UserRepository;
import com.competa.competademo.service.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.competa.competademo.dto.UserDto.from;
import static com.competa.competademo.service.impl.RoleServiceImpl.INIT_USER_ROLE;

@RequiredArgsConstructor
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final FilesStorageService filesStorageService;
    private final MailSenderService mailSenderService;
    private final UserProfileService userProfileService;
    private final ConfirmationCodeService confirmationCodeService;
    private final Configuration freemarkerConfiguration;


    @Value("${base.url}")
    private String baseUrl;

    @Value("${expire.date}")
    private Long expireDate;

    @Override
    public void resetPassword(PassChangeRequestDto passChangeRequestDto) {
        User user = getAuthUser();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateTime = localDateTime.format(formatter);

        if (!passwordEncoder.matches(passChangeRequestDto.oldPassword(), user.getPassword())) {
            mailSenderService.sendMail(user.getEmail(),
                    "COMPETA security system message",
                    "A password reset attempt has been detected at " + dateTime);
            throw new IncorrectPasswordExeption("Incorrect old password");
        }
        if (passChangeRequestDto.newPassword().equals(passChangeRequestDto.oldPassword())) {
            throw new EqualPasswordsExeption("New password should be different from the old password");
        }
        String hashedPassword = passwordEncoder.encode(passChangeRequestDto.newPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        mailSenderService.sendMail(user.getEmail(),
                "COMPETA security system message",
                "Your password has been changed");
    }


    @Transactional
    @Override
    public UserDto saveUser(@NonNull CreateUserDto userDto) {

        if (isUserByEmailExist(userDto.getEmail())) {
            log.warn("Attempted to save user with existing email: {}", userDto.getEmail());
            throw new UserAlreadyExistsException("User with this email '{}' already exists", userDto.getEmail());
        }

        final User user = userDto.toEntity();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserStatus(UserStatus.NOT_CONFIRMED);

        final Role role = roleService.findRoleByNameAsOptional(INIT_USER_ROLE)
                .orElseGet(roleService::checkRoleExist);
        user.addRole(role);

        String codeValue = confirmationCodeService.createCode(user);
        String link = baseUrl + "/api/user/email-confirmation/" + codeValue;
        String html = createMail(link);

        mailSenderService.sendMail(userDto.getEmail(), "Registration Confirmation", html);

        User newUser = saveUser(user);
        log.debug("Saved new user with email: {}", newUser.getEmail());
        UserProfile createdProfile = userProfileService.createUserProfileForUser(newUser);
        user.setUserProfile(createdProfile);

        log.debug("Created profile for new user with email: {}, profileID: {}", newUser.getEmail(), createdProfile.getId());
        return UserDto.from(newUser);
    }

    private String createMail(String link) {
        String html;
        try {
            Template template = freemarkerConfiguration.getTemplate("confirm_mail.ftlh");

            Map<String, Object> model = new HashMap<>();
            model.put("link", link);
            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return html;
    }

    @Override
    @Transactional
    public UserDto confirm(String code) {

        ConfirmationCode confirmationCode = confirmationCodeService.confirm(code);

        User user = updateUserStatus(confirmationCode.getUser().getId(), UserStatus.CONFIRMED);

        confirmationCode.setIsUsed(true);
        confirmationCode.setTimeOfUseCode(LocalDateTime.now());
        user.setUserStatus(UserStatus.CONFIRMED);
        userRepository.save(user);

        String html = createConfirmMail(user);
        mailSenderService.sendMail(user.getEmail(), "Registration Confirmation", html);
        return UserDto.from(user,user.getUserStatus());

    }

    @Override
    @Transactional
    public User updateUserStatus(@NonNull final Long userId,
                                 @NonNull final UserStatus updatedStatus) {
        User user = findById(userId);
        log.debug("Sets user status {} for user with id: '{}'", updatedStatus, userId);
        user.setUserStatus(updatedStatus);
        return userRepository.save(user);
    }

    private String createConfirmMail(User user) {
        String html;
        try {
            Template template = freemarkerConfiguration.getTemplate("confirm_text.ftlh");

            Map<String, Object> model = new HashMap<>();
            model.put("user", user.getNickName());
            model.put("yourRegisteredEmail", user.getEmail());
            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return html;
    }

    @Transactional
    @Override
    public UserDto saveUser(NewUserDto createUserDto) {
        return saveUser(CreateUserDto.to(createUserDto));
    }

    @Transactional
    @Override
    public User addUserRole(final long userId, final String roleName) {
        final User user = findById(userId);
        final Role roleToAdd = roleService.findRoleByName(roleName);

        final Set<Role> userRoles = user.getRoles();
        userRoles.add(roleToAdd);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User removeUserRole(final long userId, final String roleName) {
        final User user = findById(userId);
        final Role roleToRemove = roleService.findRoleByName(roleName);

        user.getRoles().remove(roleToRemove);
        return userRepository.save(user);
    }

    @Override
    public boolean isUserByEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional
    @Override
    public User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id '{}' not found", userId));
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .toList();
    }

    /**
     * Saves a User object in the UserRepository.
     *
     * @param user the User object to be saved
     * @return the saved User object
     */
    @Transactional
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email '%s' not found", email)));
    }

    @Override
    public User getAuthUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // вызов контекста
        final String authUserEmail = authentication.getName(); // получение имени текущего пользователя
        return findByEmail(authUserEmail);
    }

    @Override
    public UserDto getUser(Long userId) {
        final User user = getUserOrThrow(userId);
        return getAvatarBase64(user)
                .map(b64Image -> from(user, b64Image))
                .orElseGet(() -> from(user));
    }

    /**
     * Retrieves the Base64 representation of the user's avatar image, if available.
     *
     * @param user the User object
     * @return an Optional containing the Base64 image string if the avatar is available, otherwise an empty Optional
     */
    private Optional<String> getAvatarBase64(User user) {
        final ImageInfo profileAvatar = user.getUserProfile().getProfileAvatar();
        if (profileAvatar != null && profileAvatar.getUrl() != null) {
            final String base64Image = filesStorageService.getBase64Image(Path.of(profileAvatar.getUrl()));
            return Optional.ofNullable(base64Image);
        }
        return Optional.empty();
    }

    /**
     * Retrieves a user from the database by the given user ID. If the user is not found, it throws a RestApiException with a 404 status code.
     *
     * @param userId the ID of the user to retrieve
     * @return the User object corresponding to the given ID
     * @throws RestApiException if the user with the given ID is not found
     */
    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new RestApiException(HttpStatus.NOT_FOUND, "User with id <" + userId + "> not found"));
    }


    // TODO to remove
    @Override
    public List<UserDto> getUsersList(List<User> users, List<String> avatarImageDataList) {
        if (users.size() != avatarImageDataList.size()) {
            throw new RestApiException(HttpStatus.BAD_REQUEST, "Number of users and avatar images do not match");
        }
        List<UserDto> userDtos = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            String avatarImageData = avatarImageDataList.get(i);
            UserDto userDto = from(user, avatarImageData);
            userDtos.add(userDto);
        }
        return userDtos;
    }
}
