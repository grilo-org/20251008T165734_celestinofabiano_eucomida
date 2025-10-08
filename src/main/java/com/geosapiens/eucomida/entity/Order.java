package com.geosapiens.eucomida.entity;

import com.geosapiens.eucomida.constant.ValidationConstants;
import com.geosapiens.eucomida.dto.OrderRequestDto;
import com.geosapiens.eucomida.entity.enums.OrderStatus;
import com.geosapiens.eucomida.entity.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @NotNull(message = ValidationConstants.ORDER_USER_REQUIRED)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_user"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "courier_id", foreignKey = @ForeignKey(name = "fk_order_courier"))
    private Courier courier;

    @NotNull(message = ValidationConstants.ORDER_STATUS_REQUIRED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status = OrderStatus.PENDING;

    @NotNull(message = ValidationConstants.ORDER_PAYMENT_STATUS_REQUIRED)
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 30)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @NotNull(message = ValidationConstants.ORDER_TOTAL_PRICE_REQUIRED)
    @Positive(message = ValidationConstants.ORDER_TOTAL_PRICE_POSITIVE)
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    public void updateFromRequest(OrderRequestDto request, Courier courier) {
        this.status = request.status();
        this.paymentStatus = request.paymentStatus();
        this.totalPrice = request.totalPrice();
        this.courier = courier;
    }
}
