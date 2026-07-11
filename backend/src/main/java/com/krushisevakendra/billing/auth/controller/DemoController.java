package com.krushisevakendra.billing.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin(){
        return "Welcome Admin";
    }

    @GetMapping("/manager")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public String manager(){
        return "Welcome Manager";
    }

    @GetMapping("/staff")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'STAFF')")
    public String staff(){
        return "Welcome Staff";
    }
}
