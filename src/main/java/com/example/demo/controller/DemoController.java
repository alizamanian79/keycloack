package com.example.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('client_admin')")
    public String hello() {
        return "Hello from Spring boot & Keycloak";
    }

    @GetMapping("/hello-2")
    @PreAuthorize("hasRole('client_admin')")
    public String hello2() {
        return "Hello from Spring boot & Keycloak - ADMIN";
    }


    @GetMapping("/gettoken")
    @PreAuthorize("hasAnyRole('client_admin')")
    public Jwt testtoken(@AuthenticationPrincipal Jwt jwt) {
        return jwt;
    }


}
