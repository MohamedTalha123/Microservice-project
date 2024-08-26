package com.hps.user_service.service;

import com.hps.user_service.dto.UserRequest;
import com.hps.user_service.dto.UserResponse;
import com.hps.user_service.exception.UserNotFoundException;
import com.hps.user_service.entity.User;
import com.hps.user_service.mapper.UserMapper;
import com.hps.user_service.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public Long createUser(UserRequest request) {
        var user = userRepository.save(userMapper.toUser(request));
        return user.getId();
    }

    public void updateUser(UserRequest request) {
        var user = userRepository.findById(request.id())
                .orElseThrow(()-> new UserNotFoundException(
                        String.format("Cannot update user:: No user found with the provided ID: %s ", request.id())
                ));
        mergeUser(user,request);
        userRepository.save(userMapper.toUser(request));
    }

    private void mergeUser(User user, UserRequest request) {
        if (StringUtils.isNotBlank(request.userName())) {
            user.setUserName(request.userName());
        }
        if (StringUtils.isNotBlank(request.email())) {
            user.setEmail(request.email());
        }
    }

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::fromUser)
                .collect(Collectors.toList());
    }

    public Boolean existById(Long userId) {
        return userRepository.findById(userId)
                .isPresent();
    }

    public UserResponse findById(Long userId) {
        return userRepository.findById(userId)
                .stream()
                .map(userMapper::fromUser)
                .findAny().orElseThrow( () -> new UserNotFoundException(
                      String.format("No user found with the provided ID:: %s",userId))
        );
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    public User findUserByPhone(String phone){
        return userRepository.findUserByPhone(phone);
    }
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
}
