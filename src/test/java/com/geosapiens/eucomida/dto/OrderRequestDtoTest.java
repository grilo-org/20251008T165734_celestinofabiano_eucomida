package com.geosapiens.eucomida.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.geosapiens.eucomida.constant.ValidationConstants;
import com.geosapiens.eucomida.entity.enums.OrderStatus;
import com.geosapiens.eucomida.entity.enums.PaymentStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldValidateValidOrderRequestDto() {
        OrderRequestDto dto = new OrderRequestDto(
                OrderStatus.PENDING,
                PaymentStatus.PAID,
                BigDecimal.valueOf(50.00),
                UUID.randomUUID()
        );

        Set<ConstraintViolation<OrderRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldInvalidateNullStatus() {
        OrderRequestDto dto = new OrderRequestDto(
                null,
                PaymentStatus.PAID,
                BigDecimal.valueOf(50.00),
                UUID.randomUUID()
        );

        Set<ConstraintViolation<OrderRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.ORDER_STATUS_REQUIRED);
    }

    @Test
    void shouldInvalidateNullPaymentStatus() {
        OrderRequestDto dto = new OrderRequestDto(
                OrderStatus.PENDING,
                null,
                BigDecimal.valueOf(50.00),
                UUID.randomUUID()
        );

        Set<ConstraintViolation<OrderRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.ORDER_PAYMENT_STATUS_REQUIRED);
    }

    @Test
    void shouldInvalidateNullTotalPrice() {
        OrderRequestDto dto = new OrderRequestDto(
                OrderStatus.PENDING,
                PaymentStatus.PAID,
                null,
                UUID.randomUUID()
        );

        Set<ConstraintViolation<OrderRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.ORDER_TOTAL_PRICE_REQUIRED);
    }

    @Test
    void shouldInvalidateNegativeTotalPrice() {
        OrderRequestDto dto = new OrderRequestDto(
                OrderStatus.PENDING,
                PaymentStatus.PAID,
                BigDecimal.valueOf(-10.00),
                UUID.randomUUID()
        );

        Set<ConstraintViolation<OrderRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.ORDER_TOTAL_PRICE_POSITIVE);
    }

    @Test
    void shouldInvalidateZeroTotalPrice() {
        OrderRequestDto dto = new OrderRequestDto(
                OrderStatus.PENDING,
                PaymentStatus.PAID,
                BigDecimal.ZERO,
                UUID.randomUUID()
        );

        Set<ConstraintViolation<OrderRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.ORDER_TOTAL_PRICE_POSITIVE);
    }
}
