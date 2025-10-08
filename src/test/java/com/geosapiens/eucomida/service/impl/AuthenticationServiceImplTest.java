package com.geosapiens.eucomida.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.geosapiens.eucomida.dto.UserRequestDto;
import com.geosapiens.eucomida.dto.UserResponseDto;
import com.geosapiens.eucomida.exception.AuthenticatedUserNotFoundException;
import com.geosapiens.eucomida.exception.UserNotFoundException;
import com.geosapiens.eucomida.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    private MockedStatic<AuthenticationServiceImpl> AuthenticationServiceImplMock;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private static final String USER_EMAIL = "user@example.com";
    private static final String USER_NAME = "User Name";

    @BeforeEach
    void setUp() {
        AuthenticationServiceImplMock = mockStatic(AuthenticationServiceImpl.class);
    }

    @AfterEach
    void tearDown() {
        AuthenticationServiceImplMock.close();
    }

    @Test
    void shouldFindCurrentUserDtoWhenAuthenticated() {
        AuthenticationServiceImplMock.when(AuthenticationServiceImpl::getAuthentication)
                .thenReturn(Optional.of(authentication));
        AuthenticationServiceImplMock.when(
                        () -> AuthenticationServiceImpl.getClaim(AuthenticationServiceImpl.CLAIM_EMAIL))
                .thenReturn(Optional.of(USER_EMAIL));
        AuthenticationServiceImplMock.when(
                        () -> AuthenticationServiceImpl.getClaim(AuthenticationServiceImpl.CLAIM_NAME))
                .thenReturn(Optional.of(USER_NAME));

        when(userService.getOrCreate(any(UserRequestDto.class))).thenReturn(
                new UserResponseDto(null, USER_NAME, USER_EMAIL, null));

        Optional<UserResponseDto> result = authenticationService.findCurrentUserDto();

        assertThat(result).isPresent();
        assertThat(result.get().email()).isEqualTo(USER_EMAIL);
        assertThat(result.get().name()).isEqualTo(USER_NAME);
    }

    @Test
    void shouldThrowAuthenticatedUserNotFoundExceptionWhenNotAuthenticated() {
        AuthenticationServiceImplMock.when(
                        () -> AuthenticationServiceImpl.getClaim(AuthenticationServiceImpl.CLAIM_EMAIL))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authenticationService.findCurrentUser())
                .isInstanceOf(AuthenticatedUserNotFoundException.class);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        AuthenticationServiceImplMock.when(
                        () -> AuthenticationServiceImpl.getClaim(AuthenticationServiceImpl.CLAIM_EMAIL))
                .thenReturn(Optional.of(USER_EMAIL));
        when(userService.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authenticationService.findCurrentUser())
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldReturnCurrentUserEmailIfPresent() {
        AuthenticationServiceImplMock.when(
                        () -> AuthenticationServiceImpl.getClaim(AuthenticationServiceImpl.CLAIM_EMAIL))
                .thenReturn(Optional.of(USER_EMAIL));

        Optional<String> email = authenticationService.getCurrentUserEmail();

        assertThat(email).isPresent().contains(USER_EMAIL);
    }

    @Test
    void shouldReturnCurrentTokenIfPresent() {
        String token = "token";
        AuthenticationServiceImplMock.when(AuthenticationServiceImpl::getToken)
                .thenReturn(Optional.of(token));

        Optional<String> tokenOpt = authenticationService.getCurrentToken();

        assertThat(tokenOpt).isPresent().contains(token);
    }

    @Test
    void shouldReturnEmptyWhenEmailIsNull() {
        Optional<UserResponseDto> result = authenticationService.createOrGetUser(null, null);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenEmailIsBlank() {
        Optional<UserResponseDto> result = authenticationService.createOrGetUser("", " ");
        assertThat(result).isEmpty();
    }

    @Test
    void shouldUseEmailAsNameWhenNameIsNull() {
        String email = USER_EMAIL;
        UserResponseDto expectedResponse = new UserResponseDto(null, email, email, null);
        when(userService.getOrCreate(any(UserRequestDto.class))).thenReturn(expectedResponse);

        Optional<UserResponseDto> result = authenticationService.createOrGetUser(null, email);

        assertThat(result).isPresent();
        assertThat(result.get().name()).isEqualTo(email);
        assertThat(result.get().email()).isEqualTo(email);
    }

}
