package com.geosapiens.eucomida.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.geosapiens.eucomida.dto.CourierResponseDto;
import com.geosapiens.eucomida.dto.OrderRequestDto;
import com.geosapiens.eucomida.dto.OrderResponseDto;
import com.geosapiens.eucomida.entity.Courier;
import com.geosapiens.eucomida.entity.Order;
import com.geosapiens.eucomida.entity.User;
import com.geosapiens.eucomida.entity.enums.OrderStatus;
import com.geosapiens.eucomida.entity.enums.PaymentStatus;
import com.geosapiens.eucomida.entity.enums.VehicleType;
import com.geosapiens.eucomida.mapper.OrderMapper;
import com.geosapiens.eucomida.repository.OrderRepository;
import com.geosapiens.eucomida.service.AuthenticationService;
import com.geosapiens.eucomida.service.CourierService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private CourierService courierService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private static final UUID ORDER_ID = UUID.randomUUID();
    private static final UUID COURIER_ID = UUID.randomUUID();
    private static final BigDecimal TOTAL_PRICE = BigDecimal.valueOf(189.90);
    private static final CourierResponseDto COURIER_RESPONSE_DTO = CourierResponseDto.builder()
            .id(UUID.randomUUID())
            .name("Courier Name")
            .email("email@example.com")
            .vehicleType(VehicleType.CAR)
            .plateNumber("ABC1234")
            .build();

    @Test
    void shouldCreateOrderSuccessfully() {
        OrderRequestDto request = new OrderRequestDto(OrderStatus.PENDING, PaymentStatus.PAID,
                TOTAL_PRICE, COURIER_ID);
        User user = new User();
        Courier courier = new Courier();
        Order order = new Order();
        OrderResponseDto responseDto = OrderResponseDto.builder()
                .id(ORDER_ID)
                .status(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PAID)
                .totalPrice(TOTAL_PRICE)
                .createdAt(LocalDateTime.now())
                .courier(COURIER_RESPONSE_DTO)
                .build();

        when(authenticationService.findCurrentUser()).thenReturn(user);
        when(courierService.findIfIdExists(COURIER_ID)).thenReturn(courier);
        when(orderMapper.toEntity(request, user, courier)).thenReturn(order);
        when(orderRepository.saveAndFlush(order)).thenReturn(order);
        when(orderMapper.toDTO(order)).thenReturn(responseDto);

        OrderResponseDto result = orderService.create(request);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    void shouldUpdateOrderSuccessfully() {
        OrderRequestDto request = new OrderRequestDto(OrderStatus.DELIVERED, PaymentStatus.PAID,
                TOTAL_PRICE, COURIER_ID);
        User user = new User();
        Courier courier = new Courier();
        Order existingOrder = mock(Order.class);
        OrderResponseDto responseDto = OrderResponseDto.builder()
                .id(ORDER_ID)
                .status(OrderStatus.DELIVERED)
                .paymentStatus(PaymentStatus.PAID)
                .totalPrice(TOTAL_PRICE)
                .createdAt(LocalDateTime.now())
                .courier(COURIER_RESPONSE_DTO)
                .build();

        when(authenticationService.findCurrentUser()).thenReturn(user);
        when(orderRepository.findByIdAndUserId(ORDER_ID, user.getId())).thenReturn(
                Optional.of(existingOrder));
        when(courierService.findIfIdExists(COURIER_ID)).thenReturn(courier);
        when(orderRepository.saveAndFlush(existingOrder)).thenReturn(existingOrder);
        when(orderMapper.toDTO(existingOrder)).thenReturn(responseDto);

        OrderResponseDto result = orderService.update(ORDER_ID, request);

        assertThat(result).isEqualTo(responseDto);
        verify(existingOrder).updateFromRequest(request, courier);
    }

    @Test
    void shouldFindDtoByIdForCurrentUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        Order order = new Order();
        OrderResponseDto responseDto = OrderResponseDto.builder()
                .id(ORDER_ID)
                .status(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PAID)
                .totalPrice(TOTAL_PRICE)
                .createdAt(LocalDateTime.now())
                .courier(COURIER_RESPONSE_DTO)
                .build();

        when(authenticationService.findCurrentUser()).thenReturn(user);
        when(orderRepository.findByIdAndUserId(ORDER_ID, user.getId())).thenReturn(
                Optional.of(order));
        when(orderMapper.toDTO(order)).thenReturn(responseDto);

        OrderResponseDto result = orderService.findDtoByIdForCurrentUser(ORDER_ID);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    void shouldFindAllDtosByStatusForCurrentUser() {
        User user = new User();
        Order order = new Order();
        OrderResponseDto responseDto = OrderResponseDto.builder()
                .id(ORDER_ID)
                .status(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PAID)
                .totalPrice(TOTAL_PRICE)
                .createdAt(LocalDateTime.now())
                .courier(COURIER_RESPONSE_DTO)
                .build();

        when(authenticationService.findCurrentUser()).thenReturn(user);
        when(orderRepository.findByStatusAndUserId(OrderStatus.PENDING, user.getId())).thenReturn(
                List.of(order));
        when(orderMapper.toDTO(order)).thenReturn(responseDto);

        List<OrderResponseDto> result = orderService.findAllDtosByStatusForCurrentUser(
                OrderStatus.PENDING);

        assertThat(result).containsExactly(responseDto);
    }
}
