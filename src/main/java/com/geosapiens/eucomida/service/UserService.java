package com.geosapiens.eucomida.service;

import com.geosapiens.eucomida.dto.UserRequestDto;
import com.geosapiens.eucomida.dto.UserResponseDto;
import com.geosapiens.eucomida.entity.User;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserResponseDto create(UserRequestDto userRequest);

    UserResponseDto getOrCreate(UserRequestDto userRequest);

    Optional<UserResponseDto> findDtoById(UUID id);

    Optional<UserResponseDto> findDtoByEmail(String email);

    Optional<User> findByEmail(String email);

}
