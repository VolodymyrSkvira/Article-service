package com.example.articleservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.articleservice.dto.user.UserLoginRequestDto;
import com.example.articleservice.dto.user.UserLoginResponseDto;
import com.example.articleservice.dto.user.UserRegistrationRequestDto;
import com.example.articleservice.dto.user.UserResponseDto;
import com.example.articleservice.exception.RegistrationException;
import com.example.articleservice.security.AuthenticationService;
import com.example.articleservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User management", description = "Endpoints for managing users")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(
            summary = "Register new user",
            description = "Registration of the new user"
    )
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }

    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Login of the existing user"
    )
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
