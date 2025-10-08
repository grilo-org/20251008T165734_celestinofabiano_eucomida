package com.geosapiens.eucomida.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.geosapiens.eucomida.dto.OrderRequestDto;
import com.geosapiens.eucomida.dto.OrderResponseDto;
import com.geosapiens.eucomida.entity.Courier;
import com.geosapiens.eucomida.entity.Order;
import com.geosapiens.eucomida.entity.User;
import com.geosapiens.eucomida.entity.enums.OrderStatus;
import com.geosapiens.eucomida.entity.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.annotation.Validated;

@Validated
class OrderMapperTest {

    private OrderMapper orderMapper;
    private UserMapper userMapper;
    private CourierMapper courierMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
        courierMapper = new CourierMapper();
        orderMapper = new OrderMapper(userMapper, courierMapper);
    }

    @Test
    void shouldMapOrderRequestDtoToOrderEntity() {
        UUID userId = UUID.randomUUID();
        UUID courierId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        Courier courier = new Courier();
        courier.setId(courierId);

        OrderRequestDto request = new OrderRequestDto(OrderStatus.PENDING, PaymentStatus.PAID,
                new BigDecimal("100.00"), courierId);

        Order order = orderMapper.toEntity(request, user, courier);

        assertThat(order).isNotNull();
        assertThat(order.getUser()).isEqualTo(user);
        assertThat(order.getCourier()).isEqualTo(courier);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(order.getTotalPrice()).isEqualByComparingTo("100.00");
    }

    @Test
    void shouldMapOrderEntityToOrderResponseDto() {
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID courierId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        User user = new User();
        user.setId(userId);
        user.setName("User Name");

        User courierUser = new User();
        courierUser.setId(UUID.randomUUID());
        courierUser.setName("Courier User Name");

        Courier courier = new Courier();
        courier.setId(courierId);
        courier.setUser(courierUser);

        Order order = new Order();
        order.setId(orderId);
        order.setUser(user);
        order.setCourier(courier);
        order.setStatus(OrderStatus.DELIVERED);
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setTotalPrice(new BigDecimal("150.00"));
        order.setCreatedAt(createdAt);

        OrderResponseDto responseDto = orderMapper.toDTO(order);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.id()).isEqualTo(orderId);
        assertThat(responseDto.status()).isEqualTo(OrderStatus.DELIVERED);
        assertThat(responseDto.paymentStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(responseDto.totalPrice()).isEqualByComparingTo("150.00");
        assertThat(responseDto.createdAt()).isEqualTo(createdAt);
        assertThat(responseDto.courier()).isNotNull();
    }

    @Test
    void shouldMapOrderEntityToOrderResponseDtoWithoutCourier() {
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        User user = new User();
        user.setId(userId);
        user.setName("User Name");

        Order order = new Order();
        order.setId(orderId);
        order.setUser(user);
        order.setStatus(OrderStatus.DELIVERED);
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setTotalPrice(new BigDecimal("150.00"));
        order.setCreatedAt(createdAt);

        OrderResponseDto responseDto = orderMapper.toDTO(order);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.id()).isEqualTo(orderId);
        assertThat(responseDto.status()).isEqualTo(OrderStatus.DELIVERED);
        assertThat(responseDto.paymentStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(responseDto.totalPrice()).isEqualByComparingTo("150.00");
        assertThat(responseDto.createdAt()).isEqualTo(createdAt);
        assertThat(responseDto.courier()).isNull();
    }
}