package com.hps.user_service.repository;

import com.hps.user_service.dto.UserResponse;
import com.hps.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByPhone(String phone);

    Optional<User> findByEmail(String email);


}
