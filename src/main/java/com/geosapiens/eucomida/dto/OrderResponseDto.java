package com.geosapiens.eucomida.dto;

import com.geosapiens.eucomida.entity.enums.OrderStatus;
import com.geosapiens.eucomida.entity.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderResponseDto(
    UUID id,
    OrderStatus status,
    PaymentStatus paymentStatus,
    BigDecimal totalPrice,
    LocalDateTime createdAt,
    CourierResponseDto courier
) {}
