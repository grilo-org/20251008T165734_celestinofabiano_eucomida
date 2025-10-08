package com.geosapiens.eucomida.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ErrorResponseDtoTest {

    @Test
    void shouldCreateErrorResponseDto() {
        int status = 404;
        String error = "Not Found";
        String message = "Resource not found";
        String path = "/api/resource";
        LocalDateTime timestamp = LocalDateTime.now();

        ErrorResponseDto dto = ErrorResponseDto.builder()
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .timestamp(timestamp)
                .build();

        assertThat(dto.getStatus()).isEqualTo(status);
        assertThat(dto.getError()).isEqualTo(error);
        assertThat(dto.getMessage()).isEqualTo(message);
        assertThat(dto.getPath()).isEqualTo(path);
        assertThat(dto.getTimestamp()).isEqualTo(timestamp);
    }

}
