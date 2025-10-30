package com.pikolinc.dto.item;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record ItemUpdateDTO(
        @Pattern(regexp = "^(?!\\s*$).+", message = "Name cannot be blank if provided")
        String name,
        @Pattern(regexp = "^(?!\\s*$).+", message = "Description cannot be blank if provided")
        String description,
        @Positive(message = "Price must be greater than zero")
        Double price
) {}
