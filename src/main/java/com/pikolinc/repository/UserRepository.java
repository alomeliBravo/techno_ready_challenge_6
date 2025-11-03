package com.pikolinc.repository;

import com.pikolinc.dto.user.UserResponseDTO;
import com.pikolinc.model.User;

import java.util.Optional;

public interface UserRepository extends Repository<UserResponseDTO, User, Long>{
    Optional<UserResponseDTO> findByEmail(String email);
    Boolean userExist(Long id);
    Boolean emailExist(String email);
}
