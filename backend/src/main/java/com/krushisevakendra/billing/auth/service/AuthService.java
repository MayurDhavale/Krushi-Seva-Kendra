package com.krushisevakendra.billing.auth.service;

import com.krushisevakendra.billing.auth.dto.LoginRequest;
import com.krushisevakendra.billing.auth.dto.RegisterRequest;
import com.krushisevakendra.billing.common.response.ApiResponse;

public interface AuthService {

    ApiResponse<String> register(RegisterRequest request);

    ApiResponse<?> login(LoginRequest request);
}
