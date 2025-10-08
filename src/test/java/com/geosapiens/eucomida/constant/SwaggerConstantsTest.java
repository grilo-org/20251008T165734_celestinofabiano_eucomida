package com.geosapiens.eucomida.constant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;

class SwaggerConstantsTest {

    @Test
    void shouldThrowIllegalStateExceptionWhenConstructorIsInvoked() throws Exception {
        Constructor<SwaggerConstants> constructor = SwaggerConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThatThrownBy(constructor::newInstance)
                .isInstanceOf(Exception.class)
                .satisfies(exception -> {
                    Throwable cause = exception.getCause();
                    assertThat(cause).isInstanceOf(IllegalStateException.class);
                });
    }

    @Test
    void shouldContainCorrectUnauthorizedMessage() {
        assertThat(SwaggerConstants.UNAUTHORIZED_401)
                .isEqualTo("Não autorizado - Token inválido ou expirado");
    }

    @Test
    void shouldContainCorrectNotFoundMessage() {
        assertThat(SwaggerConstants.NOT_FOUND_404)
                .isEqualTo("Recurso não encontrado");
    }

    @Test
    void shouldContainCorrectBadRequestMessage() {
        assertThat(SwaggerConstants.BAD_REQUEST_400)
                .isEqualTo("Dados inválidos na requisição");
    }

    @Test
    void shouldContainCorrectAuthUserMessages() {
        assertThat(SwaggerConstants.AUTH_USER_SUMMARY)
                .isEqualTo("Obter usuário autenticado");
        assertThat(SwaggerConstants.AUTH_USER_DESCRIPTION)
                .isEqualTo("Retorna os dados do usuário autenticado com base no token JWT ou OAuth2.");
        assertThat(SwaggerConstants.AUTH_USER_200)
                .isEqualTo("Usuário autenticado retornado com sucesso");
    }

    @Test
    void shouldContainCorrectCreateOrderMessages() {
        assertThat(SwaggerConstants.CREATE_ORDER_SUMMARY)
                .isEqualTo("Criar um novo pedido");
        assertThat(SwaggerConstants.CREATE_ORDER_DESCRIPTION)
                .isEqualTo("Cria um novo pedido com base nos dados enviados.");
        assertThat(SwaggerConstants.CREATE_ORDER_201)
                .isEqualTo("Pedido criado com sucesso");
    }

    @Test
    void shouldContainCorrectUpdateOrderMessages() {
        assertThat(SwaggerConstants.UPDATE_ORDER_SUMMARY)
                .isEqualTo("Atualizar um pedido existente");
        assertThat(SwaggerConstants.UPDATE_ORDER_DESCRIPTION)
                .isEqualTo("Atualiza um pedido específico com os novos dados enviados.");
        assertThat(SwaggerConstants.UPDATE_ORDER_204)
                .isEqualTo("Pedido atualizado com sucesso");
    }

    @Test
    void shouldContainCorrectFindOrderMessages() {
        assertThat(SwaggerConstants.FIND_ORDER_SUMMARY)
                .isEqualTo("Buscar pedido por ID");
        assertThat(SwaggerConstants.FIND_ORDER_DESCRIPTION)
                .isEqualTo("Retorna os detalhes de um pedido específico pelo ID.");
        assertThat(SwaggerConstants.FIND_ORDER_200)
                .isEqualTo("Pedido encontrado com sucesso");
    }

    @Test
    void shouldContainCorrectFindOrdersByStatusMessages() {
        assertThat(SwaggerConstants.FIND_ORDERS_BY_STATUS_SUMMARY)
                .isEqualTo("Buscar pedidos por status");
        assertThat(SwaggerConstants.FIND_ORDERS_BY_STATUS_DESCRIPTION)
                .isEqualTo("Retorna uma lista de pedidos com um status específico.");
        assertThat(SwaggerConstants.FIND_ORDERS_BY_STATUS_200)
                .isEqualTo("Pedidos encontrados com sucesso");
        assertThat(SwaggerConstants.FIND_ORDERS_BY_STATUS_400)
                .isEqualTo("Status inválido");
    }
}
