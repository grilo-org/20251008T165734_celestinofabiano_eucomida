package com.geosapiens.eucomida.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserResponseDto(UUID id, String name, String email, LocalDateTime createdAt) {

}
