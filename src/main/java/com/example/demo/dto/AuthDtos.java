package com.example.demo.dto;


import lombok.*;

public class AuthDtos {
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
    }
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Getter @AllArgsConstructor
    public static class TokenResponse {
        private String accessToken;
        private String tokenType;
        private Long expiresIn;
    }
    }


