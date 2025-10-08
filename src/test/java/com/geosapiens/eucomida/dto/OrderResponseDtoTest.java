package com.geosapiens.eucomida.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.geosapiens.eucomida.entity.enums.OrderStatus;
import com.geosapiens.eucomida.entity.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderResponseDtoTest {

    @Test
    void shouldCreateOrderResponseDto() {
        UUID id = UUID.randomUUID();
        OrderStatus status = OrderStatus.PENDING;
        PaymentStatus paymentStatus = PaymentStatus.PAID;
        BigDecimal totalPrice = BigDecimal.valueOf(100.50);
        LocalDateTime createdAt = LocalDateTime.now();
        CourierResponseDto courier = CourierResponseDto.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("johndoe@example.com")
                .build();

        OrderResponseDto dto = OrderResponseDto.builder()
                .id(id)
                .status(status)
                .paymentStatus(paymentStatus)
                .totalPrice(totalPrice)
                .createdAt(createdAt)
                .courier(courier)
                .build();

        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.status()).isEqualTo(status);
        assertThat(dto.paymentStatus()).isEqualTo(paymentStatus);
        assertThat(dto.totalPrice()).isEqualTo(totalPrice);
        assertThat(dto.createdAt()).isEqualTo(createdAt);
        assertThat(dto.courier()).isEqualTo(courier);
    }

}
