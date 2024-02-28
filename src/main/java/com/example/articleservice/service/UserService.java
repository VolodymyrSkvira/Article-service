package com.example.articleservice.service;

import com.example.articleservice.dto.user.UserRegistrationRequestDto;
import com.example.articleservice.dto.user.UserResponseDto;
import com.example.articleservice.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto registrationRequest)
            throws RegistrationException;
}
