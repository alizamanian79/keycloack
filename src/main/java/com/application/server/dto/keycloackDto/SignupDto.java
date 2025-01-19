package com.application.server.dto.keycloackDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class SignupDto extends SigninDto {
    private Boolean enabled; // Fixed spelling from 'enabaled' to 'enabled'
    private String email;


    // Getters and Setters
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
