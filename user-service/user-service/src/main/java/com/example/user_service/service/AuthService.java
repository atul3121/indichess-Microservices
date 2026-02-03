package com.example.user_service.service;

import com.example.user_service.model.User;
import com.example.user_service.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRating(250); // default rating

        User savedUser = userRepo.save(user);
        System.out.println(savedUser);
        return savedUser;
    }
}
