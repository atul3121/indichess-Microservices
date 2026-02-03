package com.example.user_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/hello")
    public String showHello() {
        return "Hello";
    }

    @GetMapping("/user/username")
    public ResponseEntity<String> getUser() {
        System.out.println("User");
        return new ResponseEntity<>("User", HttpStatus.OK);
    }
}
