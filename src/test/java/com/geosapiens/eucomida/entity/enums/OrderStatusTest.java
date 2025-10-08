package com.geosapiens.eucomida.entity.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OrderStatusTest {

    @Test
    void shouldHaveAllExpectedValues() {
        assertThat(OrderStatus.values()).containsExactly(
                OrderStatus.PENDING,
                OrderStatus.IN_PROGRESS,
                OrderStatus.DELIVERED,
                OrderStatus.CANCELED
        );
    }

    @Test
    void shouldReturnEnumValueByName() {
        assertThat(OrderStatus.valueOf("PENDING")).isEqualTo(OrderStatus.PENDING);
        assertThat(OrderStatus.valueOf("IN_PROGRESS")).isEqualTo(OrderStatus.IN_PROGRESS);
        assertThat(OrderStatus.valueOf("DELIVERED")).isEqualTo(OrderStatus.DELIVERED);
        assertThat(OrderStatus.valueOf("CANCELED")).isEqualTo(OrderStatus.CANCELED);
    }
}
