package com.example.articleservice.dto.user;

import com.example.articleservice.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(message = "Passwords should match",
        field = "password",
        fieldToMatch = "repeatPassword")
public record UserRegistrationRequestDto(
        @Email
        @NotBlank
        @Size(min = 4, max = 50)
        String email,
        @NotBlank
        @Size(min = 6, max = 60)
        String password,
        @NotBlank
        @Size(min = 6, max = 60)
        String repeatPassword,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {
}
