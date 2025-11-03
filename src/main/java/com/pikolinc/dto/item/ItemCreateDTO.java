package com.pikolinc.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemCreateDTO(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Description is required")
        String description,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        Double price
) {}
