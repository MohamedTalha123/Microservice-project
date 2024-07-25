package com.hps.user_service.dto;

public record UserResponse(
        Long id,
        String userName,
        String lastName,
        String password,
        String email,
        String role,
        String sexe 
) {
}
