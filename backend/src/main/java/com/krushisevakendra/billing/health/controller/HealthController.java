package com.krushisevakendra.billing.health.controller;

import com.krushisevakendra.billing.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<ApiResponse<String>> checkHealth(){

        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Krushi Seva Kendra Backend is running successfully.")
                .data("Application is UP")
                .build();
        return ResponseEntity.ok(response);
    }
}
