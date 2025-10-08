package com.geosapiens.eucomida.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.geosapiens.eucomida.constant.ValidationConstants;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldCreateUser() {
        User user = new User("John Doe", "johndoe@example.com");

        assertThat(user.getName()).isEqualTo("John Doe");
        assertThat(user.getEmail()).isEqualTo("johndoe@example.com");
    }

    @Test
    void shouldInvalidateBlankName() {
        User user = new User("", "johndoe@example.com");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.USER_NAME_REQUIRED);
    }

    @Test
    void shouldInvalidateNullName() {
        User user = new User(null, "johndoe@example.com");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.USER_NAME_REQUIRED);
    }

    @Test
    void shouldInvalidateBlankEmail() {
        User user = new User("John Doe", "");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.USER_EMAIL_REQUIRED);
    }

    @Test
    void shouldInvalidateNullEmail() {
        User user = new User("John Doe", null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.USER_EMAIL_REQUIRED);
    }

    @Test
    void shouldInvalidateInvalidEmailFormat() {
        User user = new User("John Doe", "invalid-email");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.USER_EMAIL_INVALID);
    }

    @Test
    void shouldValidateEqualsAndHashCode() {
        UUID id = UUID.randomUUID();

        User user1 = new User("John Doe", "johndoe@example.com");
        user1.setId(id);

        User user2 = new User("John Doe", "johndoe@example.com");
        user2.setId(id);

        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }

    @Test
    void shouldNotBeEqualIfIdsAreDifferent() {
        User user1 = new User("John Doe", "johndoe@example.com");
        user1.setId(UUID.randomUUID());

        User user2 = new User("John Doe", "johndoe@example.com");
        user2.setId(UUID.randomUUID());

        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    void shouldNotBeEqualIfIdIsNull() {
        User user1 = new User("John Doe", "johndoe@example.com");
        user1.setId(null);

        User user2 = new User("John Doe", "johndoe@example.com");
        user2.setId(UUID.randomUUID());

        assertThat(user1).isNotEqualTo(user2);
    }
}
