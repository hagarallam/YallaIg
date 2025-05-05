package com.yallaIg.yallaIg_backend.security;

import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.model.User;
import com.yallaIg.yallaIg_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(ErrorConstants.ERROR_USER_NAME_NOT_FOUND));
    }
}
