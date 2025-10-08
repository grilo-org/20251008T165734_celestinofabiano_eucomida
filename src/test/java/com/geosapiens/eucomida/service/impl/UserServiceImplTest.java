package com.geosapiens.eucomida.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.geosapiens.eucomida.dto.UserRequestDto;
import com.geosapiens.eucomida.dto.UserResponseDto;
import com.geosapiens.eucomida.entity.User;
import com.geosapiens.eucomida.mapper.UserMapper;
import com.geosapiens.eucomida.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRequestDto userRequest;
    private User user;
    private UserResponseDto userResponse;
    private static final String USER_NAME = "Test User";
    private static final String USER_EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        userRequest = new UserRequestDto(USER_NAME, USER_EMAIL);
        user = new User();
        user.setName(USER_NAME);
        user.setEmail(USER_EMAIL);
        userResponse = new UserResponseDto(user.getId(), user.getEmail(), user.getName(),
                LocalDateTime.now());
    }

    @Test
    void shouldCreateUser() {
        when(userMapper.toEntity(userRequest)).thenReturn(user);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userResponse);

        UserResponseDto result = userService.create(userRequest);

        assertThat(result).isEqualTo(userResponse);
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    void shouldGetOrCreateUserWhenUserExists() {
        when(userRepository.findByEmail(userRequest.email())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userResponse);

        UserResponseDto result = userService.getOrCreate(userRequest);

        assertThat(result).isEqualTo(userResponse);
        verify(userRepository).findByEmail(userRequest.email());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldGetOrCreateUserWhenUserDoesNotExist() {
        when(userRepository.findByEmail(userRequest.email())).thenReturn(Optional.empty());
        when(userMapper.toEntity(userRequest)).thenReturn(user);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userResponse);

        UserResponseDto result = userService.getOrCreate(userRequest);

        assertThat(result).isEqualTo(userResponse);
        verify(userRepository).findByEmail(userRequest.email());
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    void shouldFindDtoByIdWhenUserExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userResponse);

        Optional<UserResponseDto> result = userService.findDtoById(user.getId());

        assertThat(result).isPresent().contains(userResponse);
        verify(userRepository).findById(user.getId());
    }

    @Test
    void shouldFindDtoByIdWhenUserDoesNotExist() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        Optional<UserResponseDto> result = userService.findDtoById(user.getId());

        assertThat(result).isEmpty();
        verify(userRepository).findById(user.getId());
    }

    @Test
    void shouldFindDtoByEmailWhenUserExists() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userResponse);

        Optional<UserResponseDto> result = userService.findDtoByEmail(user.getEmail());

        assertThat(result).isPresent().contains(userResponse);
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void shouldFindDtoByEmailWhenUserDoesNotExist() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        Optional<UserResponseDto> result = userService.findDtoByEmail(user.getEmail());

        assertThat(result).isEmpty();
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void shouldFindByEmailWhenUserExists() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail(user.getEmail());

        assertThat(result).isPresent().contains(user);
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void shouldFindByEmailWhenUserDoesNotExist() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        Optional<User> result = userService.findByEmail(user.getEmail());

        assertThat(result).isEmpty();
        verify(userRepository).findByEmail(user.getEmail());
    }
}
