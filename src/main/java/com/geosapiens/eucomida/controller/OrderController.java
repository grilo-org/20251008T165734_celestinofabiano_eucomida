package com.geosapiens.eucomida.controller;

import static com.geosapiens.eucomida.constant.SwaggerConstants.BAD_REQUEST_400;
import static com.geosapiens.eucomida.constant.SwaggerConstants.CREATE_ORDER_201;
import static com.geosapiens.eucomida.constant.SwaggerConstants.CREATE_ORDER_DESCRIPTION;
import static com.geosapiens.eucomida.constant.SwaggerConstants.CREATE_ORDER_SUMMARY;
import static com.geosapiens.eucomida.constant.SwaggerConstants.FIND_ORDERS_BY_STATUS_200;
import static com.geosapiens.eucomida.constant.SwaggerConstants.FIND_ORDERS_BY_STATUS_DESCRIPTION;
import static com.geosapiens.eucomida.constant.SwaggerConstants.FIND_ORDERS_BY_STATUS_SUMMARY;
import static com.geosapiens.eucomida.constant.SwaggerConstants.FIND_ORDER_200;
import static com.geosapiens.eucomida.constant.SwaggerConstants.FIND_ORDER_DESCRIPTION;
import static com.geosapiens.eucomida.constant.SwaggerConstants.FIND_ORDER_SUMMARY;
import static com.geosapiens.eucomida.constant.SwaggerConstants.NOT_FOUND_404;
import static com.geosapiens.eucomida.constant.SwaggerConstants.UNAUTHORIZED_401;
import static com.geosapiens.eucomida.constant.SwaggerConstants.UPDATE_ORDER_204;
import static com.geosapiens.eucomida.constant.SwaggerConstants.UPDATE_ORDER_DESCRIPTION;
import static com.geosapiens.eucomida.constant.SwaggerConstants.UPDATE_ORDER_SUMMARY;

import com.geosapiens.eucomida.dto.OrderRequestDto;
import com.geosapiens.eucomida.dto.OrderResponseDto;
import com.geosapiens.eucomida.entity.enums.OrderStatus;
import com.geosapiens.eucomida.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(OrderController.BASE_PATH)
public class OrderController {

    public static final String BASE_PATH = "${api.path}/v1/orders";
    public static final String ID_PATH = "/{id}";

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = CREATE_ORDER_SUMMARY, description = CREATE_ORDER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = CREATE_ORDER_201),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED_401)
    })
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderRequestDto request) {
        OrderResponseDto order = orderService.create(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(ID_PATH)
                .buildAndExpand(order.id())
                .toUri();

        return ResponseEntity.created(location).body(order);
    }

    @Operation(summary = UPDATE_ORDER_SUMMARY, description = UPDATE_ORDER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = UPDATE_ORDER_204),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED_401),
            @ApiResponse(responseCode = "404", description = NOT_FOUND_404)
    })
    @PutMapping(ID_PATH)
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable UUID id,
            @Valid @RequestBody OrderRequestDto request) {
        OrderResponseDto order = orderService.update(id, request);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = FIND_ORDER_SUMMARY, description = FIND_ORDER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = FIND_ORDER_200),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED_401),
            @ApiResponse(responseCode = "404", description = NOT_FOUND_404)
    })
    @GetMapping(ID_PATH)
    public ResponseEntity<OrderResponseDto> findOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.findDtoByIdForCurrentUser(id));
    }

    @Operation(summary = FIND_ORDERS_BY_STATUS_SUMMARY, description = FIND_ORDERS_BY_STATUS_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = FIND_ORDERS_BY_STATUS_200),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED_401)
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponseDto>> findOrdersByStatus(
            @PathVariable OrderStatus status) {
        List<OrderResponseDto> orders = orderService.findAllDtosByStatusForCurrentUser(status);
        return ResponseEntity.ok(orders);
    }
}
