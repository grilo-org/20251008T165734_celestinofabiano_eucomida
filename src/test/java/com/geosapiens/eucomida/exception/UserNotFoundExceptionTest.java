package com.geosapiens.eucomida.exception;

import static com.geosapiens.eucomida.constant.ErrorMessages.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UserNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        UserNotFoundException exception = new UserNotFoundException();
        assertThat(exception).hasMessage(USER_NOT_FOUND);
    }
}
