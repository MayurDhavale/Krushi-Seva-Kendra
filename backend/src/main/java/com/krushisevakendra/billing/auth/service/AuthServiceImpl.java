package com.krushisevakendra.billing.auth.service;

import com.krushisevakendra.billing.auth.dto.RegisterRequest;
import com.krushisevakendra.billing.auth.repository.UserRepository;
import com.krushisevakendra.billing.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    @Override
    public ApiResponse<String> register(RegisterRequest request) {
        return ApiResponse.success("Registration logic will be implemented in the Next Step.",null);
    }
}
