package com.application.server.controller;

import com.application.server.dto.keycloackDto.SigninDto;
import com.application.server.dto.keycloackDto.SignupDto;
import com.application.server.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import javax.naming.Context;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private AuthenticationManager authenticationManager;
    final private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    @PreAuthorize("hasRole('client-admin')")
    public ResponseEntity<?> users(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");
        return userService.users(token);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return userService.getUserInfo(token);
    }



}
