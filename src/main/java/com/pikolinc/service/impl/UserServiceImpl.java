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
    }

    @Override
    public UserResponseDTO saveUser(@Valid UserCreateDTO dto) {
        User user = UserMapper.toEntity(dto);
        this.userRepository.save(user);
        return UserMapper.toResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new NotFoundException("No users found");
        }
        return users.stream().map(UserMapper::toResponseDTO).toList();
    }

    @Override
    public UserResponseDTO findById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return UserMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUserById(Long id, @Valid UserUpdateDTO dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        UserMapper.updateEntity(user, dto);
        userRepository.update(id, user);
        return UserMapper.toResponseDTO(user);
    }

    @Override
    public void deleteUserById(long id) {
        this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        this.userRepository.delete(id);
    }
}
