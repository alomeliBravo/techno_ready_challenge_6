package com.pikolinc.service.impl;

import com.pikolinc.dto.user.UserCreateDTO;
import com.pikolinc.dto.user.UserResponseDTO;
import com.pikolinc.dto.user.UserUpdateDTO;
import com.pikolinc.exception.NotFoundException;
import com.pikolinc.mapper.UserMapper;
import com.pikolinc.model.User;
import com.pikolinc.repository.UserRepository;
import com.pikolinc.repository.impl.JdbiUserRepository;
import com.pikolinc.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(JdbiUserRepository jdbiUserRepository) {
        this.userRepository = jdbiUserRepository;
        this.userRepository.init();
    }

    @Override
    public UserResponseDTO saveUser(@Valid UserCreateDTO dto) {
        User user = UserMapper.toEntity(dto);
        return this.userRepository.save(user);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        List<UserResponseDTO> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new NotFoundException("No users found");
        }
        return users;
    }

    @Override
    public UserResponseDTO findById(long id) {
        UserResponseDTO user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return user;
    }

    @Override
    public UserResponseDTO findByEmail(String email) {
        UserResponseDTO user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return user;
    }

    @Override
    public Boolean userExist(Long id) {
        return userRepository.userExist(id);
    }

    @Override
    public UserResponseDTO updateUserById(Long id, @Valid UserCreateDTO dto){
        UserResponseDTO user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        User updatedUser = UserMapper.responseToEntity(id,user);
        updatedUser.setName(dto.name());
        updatedUser.setEmail(dto.email());
        userRepository.update(id, updatedUser);
        return UserMapper.toResponseDTO(updatedUser);
    }

    @Override
    public UserResponseDTO patchUserById(Long id, @Valid UserUpdateDTO dto) {
        UserResponseDTO user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        User updatedUser = UserMapper.responseToEntity(id,user);
        UserMapper.updateEntity(updatedUser, dto);
        return UserMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void deleteUserById(long id) {
        this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        this.userRepository.delete(id);
    }

    @Override
    public Boolean emailExist(String email) {
        return this.userRepository.emailExist(email);
    }
}
