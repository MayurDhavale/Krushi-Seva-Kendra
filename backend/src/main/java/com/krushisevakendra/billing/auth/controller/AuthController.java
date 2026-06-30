package com.krushisevakendra.billing.auth.controller;

import com.krushisevakendra.billing.auth.dto.RegisterRequest;
import com.krushisevakendra.billing.auth.service.AuthService;
import com.krushisevakendra.billing.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request){
        return authService.register(request);
    }
}
