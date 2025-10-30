package com.pikolinc.dto.item;

public record ItemResponseDTO(
        Long id,
        String name,
        String description,
        Double price
) {}
