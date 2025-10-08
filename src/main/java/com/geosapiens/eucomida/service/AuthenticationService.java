package com.geosapiens.eucomida.service;

import com.geosapiens.eucomida.dto.UserResponseDto;
import com.geosapiens.eucomida.entity.User;
import java.util.Optional;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    Optional<UserResponseDto> findCurrentUserDto();

    User findCurrentUser();

    Optional<String> getCurrentUserEmail();

    Optional<String> getCurrentToken();

    Optional<UserResponseDto> processAuthenticatedUser(Authentication authentication);

    Optional<UserResponseDto> createOrGetUser(String name, String email);
}
