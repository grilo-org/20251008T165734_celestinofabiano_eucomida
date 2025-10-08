package com.geosapiens.eucomida.exception;

import static com.geosapiens.eucomida.constant.ErrorMessages.ORDER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OrderNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        OrderNotFoundException exception = new OrderNotFoundException();
        assertThat(exception).hasMessage(ORDER_NOT_FOUND);
    }
}