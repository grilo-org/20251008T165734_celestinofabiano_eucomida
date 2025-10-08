package com.geosapiens.eucomida.constant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;

class ValidationConstantsTest {

    @Test
    void shouldThrowIllegalStateExceptionWhenConstructorIsInvoked() throws Exception {
        Constructor<ValidationConstants> constructor = ValidationConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThatThrownBy(constructor::newInstance)
                .isInstanceOf(Exception.class)
                .satisfies(exception -> {
                    Throwable cause = exception.getCause();
                    assertThat(cause)
                            .isInstanceOf(IllegalStateException.class)
                            .hasMessage(ErrorMessages.UTILITY_CLASS);
                });
    }

    @Test
    void shouldContainCorrectCourierPlateNumberRegex() {
        assertThat(ValidationConstants.COURIER_PLATE_NUMBER_REGEX)
                .isEqualTo("^[A-Z]{3}\\d[A-Z]\\d{2}$");
    }

    @Test
    void shouldContainCorrectCourierPlateNumberMessage() {
        assertThat(ValidationConstants.COURIER_PLATE_NUMBER_MESSAGE)
                .isEqualTo("A placa deve estar no formato correto (ex: AAA1B23)");
    }

    @Test
    void shouldContainCorrectOrderValidationMessages() {
        assertThat(ValidationConstants.ORDER_USER_REQUIRED)
                .isEqualTo("O pedido deve estar associado a um usuário");
        assertThat(ValidationConstants.ORDER_STATUS_REQUIRED)
                .isEqualTo("O status do pedido é obrigatório");
        assertThat(ValidationConstants.ORDER_PAYMENT_STATUS_REQUIRED)
                .isEqualTo("O status do pagamento é obrigatório");
        assertThat(ValidationConstants.ORDER_TOTAL_PRICE_REQUIRED)
                .isEqualTo("O preço total é obrigatório");
        assertThat(ValidationConstants.ORDER_TOTAL_PRICE_POSITIVE)
                .isEqualTo("O preço total deve ser maior que zero");
    }

    @Test
    void shouldContainCorrectUserValidationMessages() {
        assertThat(ValidationConstants.USER_NAME_REQUIRED)
                .isEqualTo("O nome do usuário não pode estar em branco");
        assertThat(ValidationConstants.USER_EMAIL_REQUIRED)
                .isEqualTo("O e-mail do usuário não pode estar em branco");
        assertThat(ValidationConstants.USER_EMAIL_INVALID)
                .isEqualTo("O e-mail do usuário deve ser válido");
    }
}