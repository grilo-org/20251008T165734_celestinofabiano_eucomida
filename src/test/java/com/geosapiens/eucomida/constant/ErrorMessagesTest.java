package com.geosapiens.eucomida.constant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;

class ErrorMessagesTest {

    @Test
    void shouldThrowIllegalStateExceptionWhenConstructorIsInvoked() throws Exception {
        Constructor<ErrorMessages> constructor = ErrorMessages.class.getDeclaredConstructor();
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
    void shouldContainCorrectErrorMessageForAuthenticatedUserNotFound() {
        assertThat(ErrorMessages.AUTHENTICATED_USER_NOT_FOUND)
                .isEqualTo("Usuário autenticado não encontrado");
    }

    @Test
    void shouldContainCorrectErrorMessageForUserNotFoundInDatabase() {
        assertThat(ErrorMessages.USER_NOT_FOUND)
                .isEqualTo("Usuário não encontrado no banco de dados");
    }

    @Test
    void shouldContainCorrectErrorMessageForOrderNotFound() {
        assertThat(ErrorMessages.ORDER_NOT_FOUND)
                .isEqualTo("Pedido não encontrado");
    }

    @Test
    void shouldContainCorrectErrorMessageForCourierNotFound() {
        assertThat(ErrorMessages.COURIER_NOT_FOUND)
                .isEqualTo("Entregador não encontrado");
    }

    @Test
    void shouldContainCorrectErrorMessageForResourceNotFoundError() {
        assertThat(ErrorMessages.RESOURCE_NOT_FOUND_ERROR)
                .isEqualTo("Endpoint não encontrado no servidor");
    }

    @Test
    void shouldContainCorrectErrorMessageForNotReadableError() {
        assertThat(ErrorMessages.NOT_READABLE_ERROR)
                .isEqualTo("Não foi possível mapear os parâmetros enviados. Verifique se o conteúdo e a URL da requisição estã ocorretos");
    }

    @Test
    void shouldContainCorrectErrorMessageForValidationError() {
        assertThat(ErrorMessages.VALIDATION_ERROR)
                .isEqualTo("Erro de validação");
    }

    @Test
    void shouldContainCorrectErrorMessageForInternalServerError() {
        assertThat(ErrorMessages.INTERNAL_SERVER_ERROR)
                .isEqualTo("Erro interno do servidor");
    }
}