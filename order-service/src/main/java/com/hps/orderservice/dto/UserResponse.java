package com.hps.orderservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
   private Long id;
    private String userName;
    private String lastName;
    private String email;


}
