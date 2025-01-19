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

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticateController {


    private AuthenticationManager authenticationManager;


    final private UserService userService;

    @Autowired
    public AuthenticateController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/signin")
//    @PreAuthorize("hasAnyRole('client-admin')")
    public Map token(@RequestBody SigninDto signinDto){
        return userService.signIn(signinDto);
    }


    @PostMapping("/signup")
//  @PreAuthorize("hasAnyRole('client-admin')")
    public String signup(@RequestBody SignupDto signupDto){
        return userService.signup(signupDto);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return userService.getUserInfo(token);
    }
}
