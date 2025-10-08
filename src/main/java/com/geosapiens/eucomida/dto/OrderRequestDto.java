package com.geosapiens.eucomida.dto;

import com.geosapiens.eucomida.constant.ValidationConstants;
import com.geosapiens.eucomida.entity.enums.OrderStatus;
import com.geosapiens.eucomida.entity.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record OrderRequestDto(
        @NotNull(message = ValidationConstants.ORDER_STATUS_REQUIRED)
        OrderStatus status,

        @NotNull(message = ValidationConstants.ORDER_PAYMENT_STATUS_REQUIRED)
        PaymentStatus paymentStatus,

        @NotNull(message = ValidationConstants.ORDER_TOTAL_PRICE_REQUIRED)
        @Positive(message = ValidationConstants.ORDER_TOTAL_PRICE_POSITIVE)
        BigDecimal totalPrice,

        UUID courierId
) {

}
