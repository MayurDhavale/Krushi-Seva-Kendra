package com.krushisevakendra.billing.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private String accessToken;

    private String tokenType;

    private String username;

    private String role;
}
