package com.hps.user_service.mapper;

import com.hps.user_service.dto.UserRequest;
import com.hps.user_service.dto.UserResponse;
import com.hps.user_service.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public User toUser(UserRequest request) {
        if (request == null){
            return null ;
        }
        return User.builder()
                .id(request.id())
                .userName(request.userName())
                .email(request.email())
                .role(request.role())
                .build();
    }

    public UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getPhone()
        );
    }
}
