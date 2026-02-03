package com.example.user_service.oauth;

import com.example.user_service.model.User;
import com.example.user_service.repo.UserRepository;
import com.example.user_service.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        System.out.println(email);
        System.out.println(name);

        // Generate JWT token
        String jwt = jwtService.generateToken(name);
        System.out.println("Inside oauth Success");
        System.out.println(jwt);

        // Create or get user from DB
        User user = userRepository.getUserByEmailId(email);
        if (user == null) {
            user = new User();
            user.setEmailId(email);
            user.setUsername(name);
            userRepository.save(user);
        }

        // Store JWT in HTTP-only cookie
        Cookie jwtCookie = new Cookie("JWT", jwt);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(3600);
        jwtCookie.setSecure(false); // keep false for localhost

        response.addCookie(jwtCookie);
        response.sendRedirect("http://localhost:3000/home");
    }
}
