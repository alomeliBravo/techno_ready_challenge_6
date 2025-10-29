package com.pikolinc.repository;

import com.pikolinc.dto.user.UserResponseDTO;
import com.pikolinc.model.User;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long>{
    Optional<User> findByEmail(String email);
    int userExist(Long id);
}
