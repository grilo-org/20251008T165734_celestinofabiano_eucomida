package com.geosapiens.eucomida.service.impl;

import com.geosapiens.eucomida.dto.UserRequestDto;
import com.geosapiens.eucomida.dto.UserResponseDto;
import com.geosapiens.eucomida.entity.User;
import com.geosapiens.eucomida.mapper.UserMapper;
import com.geosapiens.eucomida.repository.UserRepository;
import com.geosapiens.eucomida.service.UserService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserResponseDto create(UserRequestDto userRequest) {
        User savedUser = userRepository.saveAndFlush(userMapper.toEntity(userRequest));
        return userMapper.toDTO(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDto getOrCreate(UserRequestDto userRequest) {
        return findDtoByEmail(userRequest.email()).orElseGet(() -> create(userRequest));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDto> findDtoById(UUID id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDto> findDtoByEmail(String email) {
        return findByEmail(email).map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
