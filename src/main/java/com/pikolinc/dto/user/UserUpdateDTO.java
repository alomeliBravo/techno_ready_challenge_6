package com.pikolinc.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserUpdateDTO (
        @Pattern(regexp = "^(?!\\\\s*$).+", message = "Name cannot be blank if provided")
        String name,
        @Pattern(regexp = "^(?!\\\\s*$).+", message = "Email cannot be blank if provided")
        @Email(message = "Email must be valid")
        String email
){}
