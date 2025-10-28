package com.pikolinc.dto.user;

import jakarta.validation.constraints.Email;

public record UserUpdateDTO (
        String name,
        @Email(message = "Email must be valid")
        String email
){}
