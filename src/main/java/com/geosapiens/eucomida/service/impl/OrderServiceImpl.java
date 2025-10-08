package com.geosapiens.eucomida.service.impl;

import com.geosapiens.eucomida.dto.OrderRequestDto;
import com.geosapiens.eucomida.dto.OrderResponseDto;
import com.geosapiens.eucomida.entity.Courier;
import com.geosapiens.eucomida.entity.Order;
import com.geosapiens.eucomida.entity.User;
import com.geosapiens.eucomida.entity.enums.OrderStatus;
import com.geosapiens.eucomida.exception.OrderNotFoundException;
import com.geosapiens.eucomida.mapper.OrderMapper;
import com.geosapiens.eucomida.repository.OrderRepository;
import com.geosapiens.eucomida.service.AuthenticationService;
import com.geosapiens.eucomida.service.CourierService;
import com.geosapiens.eucomida.service.OrderService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AuthenticationService authenticationService;
    private final CourierService courierService;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            AuthenticationService authenticationService, CourierService courierService,
            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.authenticationService = authenticationService;
        this.courierService = courierService;
        this.orderMapper = orderMapper;
    }

    @Transactional
    @Override
    public OrderResponseDto create(OrderRequestDto request) {
        User user = authenticationService.findCurrentUser();
        Courier courier = courierService.findIfIdExists(request.courierId());
        Order order = orderMapper.toEntity(request, user, courier);
        return orderMapper.toDTO(orderRepository.saveAndFlush(order));
    }

    @Transactional
    @Override
    public OrderResponseDto update(UUID id, OrderRequestDto request) {
        Order existingOrder = findByIdForCurrentUser(id);
        Courier courier = courierService.findIfIdExists(request.courierId());
        existingOrder.updateFromRequest(request, courier);
        return orderMapper.toDTO(orderRepository.saveAndFlush(existingOrder));
    }

    @Override
    @Transactional(readOnly = true)
    public Order findByIdForCurrentUser(UUID id) {
        User user = authenticationService.findCurrentUser();
        return orderRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto findDtoByIdForCurrentUser(UUID id) {
        return orderMapper.toDTO(findByIdForCurrentUser(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> findAllDtosByStatusForCurrentUser(OrderStatus status) {
        User user = authenticationService.findCurrentUser();
        return orderRepository.findByStatusAndUserId(status, user.getId())
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

}
