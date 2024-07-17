package com.hps.user_service.dto;

import lombok.NonNull;

public record UserRequest(
          Long id,
         String userName,
         String lastName,
         String password,
         String email,
         String sexe
) {
}
