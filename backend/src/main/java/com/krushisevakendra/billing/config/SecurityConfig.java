package com.krushisevakendra.billing.config;
import com.krushisevakendra.billing.auth.security.CustomUserDetailsService;
import com.krushisevakendra.billing.security.filter.JwtAuthenticationFilter;
import com.krushisevakendra.billing.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final PasswordEncoder passwordEncoder;
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
                .httpBasic(Customizer.withDefaults())

                .addFilterBefore(jwtAuthenticationFilter
                        ,UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    //Password Encoder Bean
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    //Authentication Provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(customUserDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    //Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{

        return configuration.getAuthenticationManager();
    }
}