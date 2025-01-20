package com.application.server.controller;


import com.application.server.dto.keycloackDto.SignupDto;
import com.application.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;



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
    @PreAuthorize("hasAnyRole('client-user','default-roles-myrealm')")
    public ResponseEntity<?> getToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return userService.getUserInfo(token);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('client-admin')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id,@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return userService.deleteUser(id,token);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('client-user','default-roles-myrealm')")
    public String updateUser(@RequestHeader("Authorization") String authorizationHeader, SignupDto signupDto) {
       return userService.updateUser()
    }

}
