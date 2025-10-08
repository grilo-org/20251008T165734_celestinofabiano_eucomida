package com.geosapiens.eucomida.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.geosapiens.eucomida.dto.UserRequestDto;
import com.geosapiens.eucomida.dto.UserResponseDto;
import com.geosapiens.eucomida.entity.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.annotation.Validated;

@Validated
class UserMapperTest {

    private UserMapper userMapper;
    private Validator validator;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldMapUserRequestDtoToUserEntity() {
        UserRequestDto request = new UserRequestDto("John Doe", "john.doe@example.com");

        User user = userMapper.toEntity(request);

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("John Doe");
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void shouldMapUserEntityToUserResponseDto() {
        UUID userId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        User user = new User();
        user.setId(userId);
        user.setName("Jane Doe");
        user.setEmail("jane.doe@example.com");
        user.setCreatedAt(createdAt);

        UserResponseDto responseDto = userMapper.toDTO(user);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.id()).isEqualTo(userId);
        assertThat(responseDto.name()).isEqualTo("Jane Doe");
        assertThat(responseDto.email()).isEqualTo("jane.doe@example.com");
        assertThat(responseDto.createdAt()).isEqualTo(createdAt);
    }

    @Test
    void shouldNotAllowNullName() {
        UserRequestDto request = new UserRequestDto(null, "john.doe@example.com");
        Set<jakarta.validation.ConstraintViolation<UserRequestDto>> violations = validator.validate(
                request);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void shouldNotAllowInvalidEmailPattern() {
        UserRequestDto request = new UserRequestDto("John Doe", "invalid-email");
        Set<jakarta.validation.ConstraintViolation<UserRequestDto>> violations = validator.validate(
                request);

        assertThat(violations).isNotEmpty();
    }
}