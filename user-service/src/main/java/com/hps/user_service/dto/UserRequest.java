package com.hps.user_service.dto;

import lombok.NonNull;

public record UserRequest(
        Long id,
         String userName,
         String password,
         String email,
         String role,
          String phone
) {
}
