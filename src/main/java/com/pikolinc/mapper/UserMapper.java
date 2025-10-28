package com.pikolinc.mapper;

import com.pikolinc.dto.user.UserCreateDTO;
import com.pikolinc.dto.user.UserResponseDTO;
import com.pikolinc.dto.user.UserUpdateDTO;
import com.pikolinc.model.User;

public class UserMapper {
    public static User toEntity(UserCreateDTO dto) {
        if (dto == null) return null;

        return User.builder()
                .name(dto.name())
                .email(dto.email())
                .build();
    }

    public static void updateEntity(User user, UserUpdateDTO dto) {
        if (dto == null) return;

        if (dto.name() != null) user.setName(dto.name());
        if (dto.email() != null) user.setEmail(dto.email());
    }

    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) return null;

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
