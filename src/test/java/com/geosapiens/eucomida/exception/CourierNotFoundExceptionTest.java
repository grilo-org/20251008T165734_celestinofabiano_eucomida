package com.geosapiens.eucomida.exception;

import static com.geosapiens.eucomida.constant.ErrorMessages.COURIER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CourierNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        CourierNotFoundException exception = new CourierNotFoundException();
        assertThat(exception).hasMessage(COURIER_NOT_FOUND);
    }
}
