package com.application.server.controller;

import com.application.server.dto.keycloackDto.SigninDto;
import com.application.server.dto.keycloackDto.SignupDto;
import com.application.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class UserController {

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/signin")
//    @PreAuthorize("hasAnyRole('client-admin')")
    public Map token(@RequestBody SigninDto signinDto){
    return userService.getToken(signinDto);
    }


    @PostMapping("/signup")
//  @PreAuthorize("hasAnyRole('client-admin')")
    public String signup(@RequestBody SignupDto signupDto){
        return userService.signup(signupDto);
    }



}
