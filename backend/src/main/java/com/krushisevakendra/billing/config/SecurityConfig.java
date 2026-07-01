package com.krushisevakendra.billing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //Disable CSRF for REST APIS
                .csrf(csrf->csrf.disable())

                //Stateless session (required for JWT)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //Authorization Rule
                .authorizeHttpRequests(auth -> auth

                        //Authentication APIs
                                .requestMatchers(HttpMethod.POST,
                                        "/api/v1/auth/register",
                                        "/api/v1/auth/login")
                                .permitAll()

                        //Swagger
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**")
                                .permitAll()

                        //Everything Else
                                .anyRequest()
                                .authenticated()
                )

                // Temporary Basic Auth
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}