package com.competa.competademo.security.details;

import com.competa.competademo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Oleg Karimov
 */
@RequiredArgsConstructor
@Service
public class AuthenticatedUsersService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new AuthenticatedUser(
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User with email <" + email + "> not found ")));
    }
}
