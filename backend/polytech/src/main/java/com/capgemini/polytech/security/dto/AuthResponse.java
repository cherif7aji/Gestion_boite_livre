package com.capgemini.polytech.security.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String userId;
    //    private String refreshToken;
    //    private String tokenType = "Bearer ";
    
    public AuthResponse(String accessToken,String userId) {
        this.accessToken = accessToken;
        this.userId=userId;
    //   this.refreshToken = refreshToken;
        }

    
}
