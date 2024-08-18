package com.springboot.blog.controller;

import com.springboot.blog.dto.JwtAuthResponse;
import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegistrationDto;
import com.springboot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "REST APIs for Authentication Resource"
)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {

        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegistrationDto registrationDto) {
        String response = authService.register(registrationDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
