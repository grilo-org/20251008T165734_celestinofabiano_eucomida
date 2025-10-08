package com.geosapiens.eucomida.mapper;

import com.geosapiens.eucomida.dto.OrderRequestDto;
import com.geosapiens.eucomida.dto.OrderResponseDto;
import com.geosapiens.eucomida.entity.Courier;
import com.geosapiens.eucomida.entity.Order;
import com.geosapiens.eucomida.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
public class OrderMapper {

    private final UserMapper userMapper;

    private final CourierMapper courierMapper;

    public OrderMapper(UserMapper userMapper, CourierMapper courierMapper) {
        this.userMapper = userMapper;
        this.courierMapper = courierMapper;
    }

    public @Validated Order toEntity(OrderRequestDto request, User user, Courier courier) {
        Order order = new Order();
        order.setUser(user);
        order.setCourier(courier);
        order.setStatus(request.status());
        order.setPaymentStatus(request.paymentStatus());
        order.setTotalPrice(request.totalPrice());
        return order;
    }

    public OrderResponseDto toDTO(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .courier(order.getCourier() != null ?
                        courierMapper.toDTO(order.getCourier()) : null)
                .build();
    }
}
