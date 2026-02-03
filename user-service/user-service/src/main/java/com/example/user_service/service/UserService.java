package com.example.user_service.service;

import com.example.user_service.model.User;
import com.example.user_service.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        return user != null ? user.getUsername() : null;
    }

    public User findByEmail(String email) {
        return userRepository.getUserByEmailId(email);
    }

    public User saveUser(User user) {
        // encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
