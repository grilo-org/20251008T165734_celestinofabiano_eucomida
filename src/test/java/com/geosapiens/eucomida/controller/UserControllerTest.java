package com.geosapiens.eucomida.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.geosapiens.eucomida.dto.UserResponseDto;
import com.geosapiens.eucomida.service.AuthenticationService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UserControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCurrentUserWithAuthorizationHeader() {
        UserResponseDto userResponse = mock(UserResponseDto.class);
        String token = "Bearer token";
        when(authenticationService.findCurrentUserDto()).thenReturn(Optional.of(userResponse));
        when(authenticationService.getCurrentToken()).thenReturn(Optional.of(token));

        ResponseEntity<UserResponseDto> response = userController.findCurrentUser();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userResponse);
        assertThat(response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION)).isEqualTo(token);
    }

    @Test
    void shouldReturnCurrentUserWithoutAuthorizationHeader() {
        UserResponseDto userResponse = mock(UserResponseDto.class);
        when(authenticationService.findCurrentUserDto()).thenReturn(Optional.of(userResponse));
        when(authenticationService.getCurrentToken()).thenReturn(Optional.empty());

        ResponseEntity<UserResponseDto> response = userController.findCurrentUser();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userResponse);
        assertThat(response.getHeaders()).doesNotContainKey(HttpHeaders.AUTHORIZATION);
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() {
        when(authenticationService.findCurrentUserDto()).thenReturn(Optional.empty());

        ResponseEntity<UserResponseDto> response = userController.findCurrentUser();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}
