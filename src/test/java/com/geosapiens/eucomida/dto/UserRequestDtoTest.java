package com.geosapiens.eucomida.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.geosapiens.eucomida.constant.ValidationConstants;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldCreateUserRequestDtoUsingBuilder() {
        UserRequestDto dto = UserRequestDto.builder()
                .name("John Doe")
                .email("johndoe@example.com")
                .build();

        assertThat(dto.name()).isEqualTo("John Doe");
        assertThat(dto.email()).isEqualTo("johndoe@example.com");
    }

    @Test
    void shouldValidateValidUserRequestDto() {
        UserRequestDto dto = UserRequestDto.builder()
                .name("John Doe")
                .email("johndoe@example.com")
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldInvalidateBlankName() {
        UserRequestDto dto = UserRequestDto.builder()
                .name("")
                .email("johndoe@example.com")
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.USER_NAME_REQUIRED);
    }

    @Test
    void shouldInvalidateNullName() {
        UserRequestDto dto = UserRequestDto.builder()
                .name(null)
                .email("johndoe@example.com")
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.USER_NAME_REQUIRED);
    }

    @Test
    void shouldInvalidateBlankEmail() {
        UserRequestDto dto = UserRequestDto.builder()
                .name("John Doe")
                .email("")
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.USER_EMAIL_REQUIRED);
    }

    @Test
    void shouldInvalidateNullEmail() {
        UserRequestDto dto = UserRequestDto.builder()
                .name("John Doe")
                .email(null)
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.USER_EMAIL_REQUIRED);
    }

    @Test
    void shouldInvalidateInvalidEmailFormat() {
        UserRequestDto dto = UserRequestDto.builder()
                .name("John Doe")
                .email("invalid-email")
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.USER_EMAIL_INVALID);
    }
}
