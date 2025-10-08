package com.geosapiens.eucomida.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.geosapiens.eucomida.dto.OrderRequestDto;
import com.geosapiens.eucomida.dto.OrderResponseDto;
import com.geosapiens.eucomida.entity.enums.OrderStatus;
import com.geosapiens.eucomida.exception.OrderNotFoundException;
import com.geosapiens.eucomida.service.OrderService;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void shouldCreateOrder() {
        OrderRequestDto request = mock(OrderRequestDto.class);
        OrderResponseDto response = mock(OrderResponseDto.class);
        when(response.id()).thenReturn(UUID.randomUUID());
        when(orderService.create(request)).thenReturn(response);

        ResponseEntity<OrderResponseDto> result = orderController.createOrder(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getHeaders().getLocation()).isInstanceOf(URI.class);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void shouldUpdateOrder() {
        UUID id = UUID.randomUUID();
        OrderRequestDto request = mock(OrderRequestDto.class);
        OrderResponseDto response = mock(OrderResponseDto.class);
        when(orderService.update(id, request)).thenReturn(response);

        ResponseEntity<OrderResponseDto> result = orderController.updateOrder(id, request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void shouldFindOrderById() {
        UUID id = UUID.randomUUID();
        OrderResponseDto response = mock(OrderResponseDto.class);
        when(orderService.findDtoByIdForCurrentUser(id)).thenReturn(response);

        ResponseEntity<OrderResponseDto> result = orderController.findOrderById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void shouldFindOrdersByStatus() {
        OrderStatus status = OrderStatus.PENDING;
        List<OrderResponseDto> responses = List.of(mock(OrderResponseDto.class));
        when(orderService.findAllDtosByStatusForCurrentUser(status)).thenReturn(responses);

        ResponseEntity<List<OrderResponseDto>> result = orderController.findOrdersByStatus(status);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(responses);
    }

    @Test
    void shouldReturnNotFoundWhenOrderDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(orderService.findDtoByIdForCurrentUser(id)).thenThrow(new OrderNotFoundException());

        assertThatThrownBy(() -> orderController.findOrderById(id))
                .isInstanceOf(OrderNotFoundException.class);
    }

}