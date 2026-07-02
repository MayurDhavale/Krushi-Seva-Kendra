package com.krushisevakendra.billing.auth.security;

import com.krushisevakendra.billing.auth.entity.User;
import com.krushisevakendra.billing.auth.repository.UserRepository;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(()->
                new UsernameNotFoundException("Invalid UserName or Password"));

        return new CustomUserDetails(user);
    }
}
