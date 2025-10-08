package com.geosapiens.eucomida.service;

import com.geosapiens.eucomida.dto.OrderRequestDto;
import com.geosapiens.eucomida.dto.OrderResponseDto;
import com.geosapiens.eucomida.entity.Order;
import com.geosapiens.eucomida.entity.enums.OrderStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {

    OrderResponseDto create(OrderRequestDto request);

    OrderResponseDto update(UUID id, OrderRequestDto request);

    @Transactional(readOnly = true)
    Order findByIdForCurrentUser(UUID id);

    OrderResponseDto findDtoByIdForCurrentUser(UUID id);

    List<OrderResponseDto> findAllDtosByStatusForCurrentUser(OrderStatus status);
}
