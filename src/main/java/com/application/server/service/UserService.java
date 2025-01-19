package com.application.server.service;

import com.application.server.dto.keycloackDto.SigninDto;
import com.application.server.dto.keycloackDto.SignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserService {

    @Value("${keycloack.realmName}")
    private String realm;

    @Value("${keycloack.client-id}")
    private String clientId;

    @Value("${keycloack.client-secret}")
    private String clientSecret;


    @Value("${keycloack.grant-type}")
    private String grantType;


    @Value("${keycloack.username}")
    private String username;


    @Value("${keycloack.password}")
    private String password;


    @Autowired
    private RestTemplate restTemplate;

    public String getToken(SigninDto signinDto) {
        String url = "http://localhost:8080/realms/myrealm/protocol/openid-connect/token";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // If you have a Bearer token to include, set it here
        String token = null;
        String bearerToken = token; // Replace with actual token if needed
        headers.set("Authorization", "Bearer " + bearerToken);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType); // Ensure grant type is defined
        params.add("client_id", clientId);
        params.add("client_secret",clientSecret);
        params.add("username", signinDto.getUsername());
        params.add("password", signinDto.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
            // Check if the response body is not null and contains the access_token
            if (response.getBody() != null && response.getBody().containsKey("access_token")) {
                return (String) response.getBody().get("access_token");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log the error)
            e.printStackTrace(); // Replace with proper error handling
        }

        // Return null if access_token is not found or an exception occurs
        return null;
    }

    public String signup(SignupDto signupDto) {
        SigninDto signinDto = new SigninDto();
        signinDto.setUsername(username);
        signinDto.setPassword(password);

        // Get the token (ensure `getToken` works properly)
        String token = getToken(signinDto);
        if (token == null || token.isEmpty()) {
            System.out.println("Failed to fetch token.");
            return null;
        }




        // REST API URL for creating users
        String url = "http://localhost:8080/admin/realms/myrealm/users";
        RestTemplate restTemplate = new RestTemplate();

        // Setting up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // JSON for creating users
        headers.set("Authorization", "Bearer " + token);

        // Request payload
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("username", signupDto.getUsername());
        userPayload.put("enabled", signupDto.getEnabled());
        userPayload.put("email", signupDto.getEmail());
        userPayload.put("credentials", List.of(
                Map.of(
                        "type", "password",
                        "value", signupDto.getPassword(),
                        "temporary", false
                )
        ));

        // Wrapping the payload in HttpEntity
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);

        try {
            // Sending the POST request
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("User successfully created.");
                return "User successfully created.";
            } else {
                System.out.println("Failed to create user: " + response.getBody());
                return "Failed to create user: " + response.getBody();
            }
        } catch (Exception e) {
            // Handle exceptions appropriately
            System.err.println("Error occurred during signup: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Return null if user creation fails
    }


//    public String signIn(SigninDto signinDto) throws IOException {
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody body = RequestBody.create(mediaType,
//                "client_id=" + clientId +
//                        "&grant_type=" + grantType +
//                        "&client_secret=" + clientSecret +
//                        "&scope=" + "openid" +
//                        "&username=" + signinDto.getUsername() +
//                        "&password=" + signinDto.getPassword());
//
//        Request request = new Request.Builder()
//                .url("http://localhost:8080/realms/" + realm + "/protocol/openid-connect/token")
//                .post(body)
//                .addHeader("cookie", "JSESSIONID=951AB835B75D7056240DE02A2310E5DF")
//                .addHeader("content-type", "application/x-www-form-urlencoded")
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                throw new IOException("Unexpected code " + response);
//            }
//            return response.body().string();
//        }
//    }

//    public String signIn(SigninDto signinDto) throws IOException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.));
//        HttpEntity<SigninDto> entity = new HttpEntity<SigninDto>(signinDto,headers);
//        return restTemplate.exchange(
//                "http://localhost:8080/products", HttpMethod.POST, entity, String.class).getBody();
//
//    }


//    public ResponseEntity<?> signUp(String token) {
//        String url = "http://localhost:8080/admin/realms/myrealm/users";
//
//        // Create the request body as a Map
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("username", "osal");
//        requestBody.put("enabled", true);
//        requestBody.put("email", "Mohadese@example.com");
//        requestBody.put("firstName", "test");
//        requestBody.put("lastName", "test");
//
//        Map<String, Object> credential = new HashMap<>();
//        credential.put("type", "password");
//        credential.put("value", "9898");
//        credential.put("temporary", false);
//
//        requestBody.put("credentials", new Object[]{credential});
//
//        // Create the HTTP headers with the Bearer token
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(token);
//
//        // Create the HTTP entity with the request body and headers
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//
//        // Create the RestTemplate and make the POST request
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
