package com.geosapiens.eucomida.entity.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PaymentStatusTest {

    @Test
    void shouldHaveAllExpectedValues() {
        assertThat(PaymentStatus.values()).containsExactly(
                PaymentStatus.PENDING,
                PaymentStatus.PAID,
                PaymentStatus.FAILED
        );
    }

    @Test
    void shouldReturnEnumValueByName() {
        assertThat(PaymentStatus.valueOf("PENDING")).isEqualTo(PaymentStatus.PENDING);
        assertThat(PaymentStatus.valueOf("PAID")).isEqualTo(PaymentStatus.PAID);
        assertThat(PaymentStatus.valueOf("FAILED")).isEqualTo(PaymentStatus.FAILED);
    }
}
