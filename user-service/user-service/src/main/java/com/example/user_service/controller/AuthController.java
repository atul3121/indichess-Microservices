package com.example.user_service.controller;

import com.example.user_service.model.DTO.LoginDto;
import com.example.user_service.model.DTO.LoginResponseDto;
import com.example.user_service.model.User;
import com.example.user_service.service.AuthService;
import com.example.user_service.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final AuthService authservice;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("signup")
    public ResponseEntity<User> handleSignup(@RequestBody User user) {
        return new ResponseEntity<>(authservice.save(user), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<?> handleLogin(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestBody LoginDto loginDto) throws IOException {

        Authentication authObject = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        if (authObject.isAuthenticated()) {
            String tk = jwtService.generateToken(loginDto.getUsername());

            ResponseCookie cookie = ResponseCookie.from("JWT", tk)
                    .httpOnly(true)
                    .secure(false)
                    .sameSite("lax")
                    .path("/")
                    .maxAge(3600)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(tk);
        }

        return new ResponseEntity<>(
                new LoginResponseDto(null, "Auth Failed"),
                HttpStatus.BAD_REQUEST
        );
    }

    @GetMapping("home")
    public ResponseEntity<?> handleHome() {
        return ResponseEntity.ok("Home");
    }
}
