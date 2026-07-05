package com.krushisevakendra.billing.auth.service;

import com.krushisevakendra.billing.auth.dto.LoginRequest;
import com.krushisevakendra.billing.auth.dto.LoginResponse;
import com.krushisevakendra.billing.auth.dto.RegisterRequest;
import com.krushisevakendra.billing.auth.entity.Role;
import com.krushisevakendra.billing.auth.entity.User;
import com.krushisevakendra.billing.auth.repository.UserRepository;
import com.krushisevakendra.billing.auth.security.CustomUserDetails;
import com.krushisevakendra.billing.common.response.ApiResponse;
import com.krushisevakendra.billing.exception.DuplicateResourceException;
import com.krushisevakendra.billing.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public ApiResponse<String> register(RegisterRequest request) {

        if(userRepository.existsByUsername(request.getUsername())){
            throw new DuplicateResourceException("Username already exist");
        }

        if(userRepository.existsByEmail(request.getEmail())){
            throw new DuplicateResourceException("Email already exist");
        }

        User user = User.builder()
                .name(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .enabled(true)
                .build();

        userRepository.save(user);

        return ApiResponse.success("User registered successfully", HttpStatus.CREATED.value(), null);

    }

    @Override
    public ApiResponse<?> login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        LoginResponse response = LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .username(userDetails.getUsername())
                .role(userDetails.getUser().getRole().name())
                .build();


        return ApiResponse.success("Login successful.",response);
    }
}
