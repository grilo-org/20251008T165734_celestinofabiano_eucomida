package com.geosapiens.eucomida.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.geosapiens.eucomida.constant.ValidationConstants;
import com.geosapiens.eucomida.dto.OrderRequestDto;
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

class OrderTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldCreateOrder() {
        User user = new User();
        user.setId(UUID.randomUUID());
        Courier courier = new Courier();
        courier.setId(UUID.randomUUID());

        Order order = new Order(user, courier, OrderStatus.PENDING, PaymentStatus.PAID, BigDecimal.valueOf(100.00));

        assertThat(order.getUser()).isEqualTo(user);
        assertThat(order.getCourier()).isEqualTo(courier);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(order.getTotalPrice()).isEqualTo(BigDecimal.valueOf(100.00));
    }

    @Test
    void shouldInvalidateNullUser() {
        Order order = new Order(null, null, OrderStatus.PENDING, PaymentStatus.PAID, BigDecimal.valueOf(100.00));

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.ORDER_USER_REQUIRED);
    }

    @Test
    void shouldInvalidateNullStatus() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Order order = new Order(user, null, null, PaymentStatus.PAID, BigDecimal.valueOf(100.00));

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.ORDER_STATUS_REQUIRED);
    }

    @Test
    void shouldInvalidateNullPaymentStatus() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Order order = new Order(user, null, OrderStatus.PENDING, null, BigDecimal.valueOf(100.00));

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.ORDER_PAYMENT_STATUS_REQUIRED);
    }

    @Test
    void shouldInvalidateNullTotalPrice() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Order order = new Order(user, null, OrderStatus.PENDING, PaymentStatus.PAID, null);

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.ORDER_TOTAL_PRICE_REQUIRED);
    }

    @Test
    void shouldInvalidateNegativeTotalPrice() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Order order = new Order(user, null, OrderStatus.PENDING, PaymentStatus.PAID, BigDecimal.valueOf(-50.00));

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.ORDER_TOTAL_PRICE_POSITIVE);
    }

    @Test
    void shouldUpdateFromRequest() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Courier courier = new Courier();
        courier.setId(UUID.randomUUID());

        Order order = new Order(user, null, OrderStatus.PENDING, PaymentStatus.PENDING, BigDecimal.valueOf(50.00));

        OrderRequestDto request = new OrderRequestDto(OrderStatus.IN_PROGRESS, PaymentStatus.PAID, BigDecimal.valueOf(120.00), null);

        order.updateFromRequest(request, courier);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.IN_PROGRESS);
        assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(order.getTotalPrice()).isEqualTo(BigDecimal.valueOf(120.00));
        assertThat(order.getCourier()).isEqualTo(courier);
    }

    @Test
    void shouldValidateEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(UUID.randomUUID());

        Order order1 = new Order(user, null, OrderStatus.PENDING, PaymentStatus.PAID, BigDecimal.valueOf(100.00));
        order1.setId(id);

        Order order2 = new Order(user, null, OrderStatus.PENDING, PaymentStatus.PAID, BigDecimal.valueOf(100.00));
        order2.setId(id);

        assertThat(order1).isEqualTo(order2);
        assertThat(order1.hashCode()).isEqualTo(order2.hashCode());
    }

    @Test
    void shouldNotBeEqualIfIdsAreDifferent() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Order order1 = new Order(user, null, OrderStatus.PENDING, PaymentStatus.PAID, BigDecimal.valueOf(100.00));
        order1.setId(UUID.randomUUID());

        Order order2 = new Order(user, null, OrderStatus.PENDING, PaymentStatus.PAID, BigDecimal.valueOf(100.00));
        order2.setId(UUID.randomUUID());

        assertThat(order1).isNotEqualTo(order2);
    }

    @Test
    void shouldNotBeEqualIfIdIsNull() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Order order1 = new Order(user, null, OrderStatus.PENDING, PaymentStatus.PAID, BigDecimal.valueOf(100.00));
        order1.setId(null);

        Order order2 = new Order(user, null, OrderStatus.PENDING, PaymentStatus.PAID, BigDecimal.valueOf(100.00));
        order2.setId(UUID.randomUUID());

        assertThat(order1).isNotEqualTo(order2);
    }
}
