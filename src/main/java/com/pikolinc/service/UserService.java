package com.pikolinc.service;

import com.pikolinc.dto.user.UserCreateDTO;
import com.pikolinc.dto.user.UserResponseDTO;
import com.pikolinc.dto.user.UserUpdateDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO saveUser(UserCreateDTO dto);
    List<UserResponseDTO> findAll();
    UserResponseDTO findById(long id);
    UserResponseDTO findByEmail(String email);
    Boolean userExist(Long id);
    UserResponseDTO updateUserById(Long id, UserUpdateDTO dto);
    void deleteUserById(long id);
}
