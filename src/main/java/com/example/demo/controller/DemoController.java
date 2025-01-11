package com.example.demo.controller;

import com.example.demo.Dto.AuthResponseDTO;
import com.example.demo.Dto.SigninDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @Value("${keycloack.realmName}")
    public String realm;

    @Value("${keycloack.clientId}")
    public String clientId;

    @Value("${keycloack.grantType}")
    public String grantType;


    final RestTemplate restTemplate;
    @Autowired
    public DemoController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('client_admin')")
    public String hello() {
        return "Hello from Spring boot & Keycloak";
    }

    @GetMapping("/getToken")
    @PreAuthorize("hasAnyRole('client_admin')")
    public ResponseEntity<?> getToken() {
        String url = "http://localhost:8080/realms/hovitarealms/protocol/openid-connect/token";
        String requestBody = "${grant_type=password&client_id=hovita-cli&client_secret=H9ZbJAX1PlMnkwVD2R5y0zsO9C7w4MZQ&username=admin&password=admin";

        // Prepare the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Prepare the HttpEntity with body and headers
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);


        try {
            // Send the request to Keycloak to get the token
            ResponseEntity<AuthResponseDTO> response = restTemplate.exchange(url, HttpMethod.POST, entity, AuthResponseDTO.class);

            // Return the response body (token)
            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            // Handle any errors that might occur during the request
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving token: " + e.getMessage());
        }
    }


}
