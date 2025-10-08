package com.geosapiens.eucomida.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserResponseDtoTest {

    @Test
    void shouldCreateUserResponseDtoUsingBuilder() {
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "johndoe@example.com";
        LocalDateTime createdAt = LocalDateTime.now();

        UserResponseDto dto = UserResponseDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .createdAt(createdAt)
                .build();

        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.name()).isEqualTo(name);
        assertThat(dto.email()).isEqualTo(email);
        assertThat(dto.createdAt()).isEqualTo(createdAt);
    }
}
