package com.studentperformance.service.impl;

import com.studentperformance.dto.LoginRequestDTO;
import com.studentperformance.dto.RegisterRequestDTO;
import com.studentperformance.exception.ResourceAlreadyExistsException;
import com.studentperformance.model.User;
import com.studentperformance.repository.UserRepository;
import com.studentperformance.security.JwtUtil;
import com.studentperformance.service.AuthenticateService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtils;

    public AuthenticateServiceImpl(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder,
                                   AuthenticationManager authenticationManager,
                                   JwtUtil jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User register(RegisterRequestDTO registerRequest) {
        // Check if username/email already exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already taken!");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered!");
        }

        // Create new user
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role("ROLE_USER")
                .build();

        return userRepository.save(user);
    }

    @Override
    public String login(LoginRequestDTO loginRequest) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // ✅ FIXED: pass username (String), not Authentication
        return jwtUtils.generateToken(loginRequest.getUsername());
    }
}
