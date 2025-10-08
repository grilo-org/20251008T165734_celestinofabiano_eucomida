package com.geosapiens.eucomida.exception;

import static com.geosapiens.eucomida.constant.ErrorMessages.AUTHENTICATED_USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AuthenticatedUserNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        AuthenticatedUserNotFoundException exception = new AuthenticatedUserNotFoundException();
        assertThat(exception).hasMessage(AUTHENTICATED_USER_NOT_FOUND);
    }

}