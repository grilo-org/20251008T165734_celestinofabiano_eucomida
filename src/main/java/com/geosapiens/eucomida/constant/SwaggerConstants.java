package com.geosapiens.eucomida.constant;

import static com.geosapiens.eucomida.constant.ErrorMessages.UTILITY_CLASS;

public class SwaggerConstants {

    public static final String UNAUTHORIZED_401 = "Não autorizado - Token inválido ou expirado";
    public static final String NOT_FOUND_404 = "Recurso não encontrado";
    public static final String BAD_REQUEST_400 = "Dados inválidos na requisição";

    public static final String AUTH_USER_SUMMARY = "Obter usuário autenticado";
    public static final String AUTH_USER_DESCRIPTION = "Retorna os dados do usuário autenticado com base no token JWT ou OAuth2.";
    public static final String AUTH_USER_200 = "Usuário autenticado retornado com sucesso";

    public static final String CREATE_ORDER_SUMMARY = "Criar um novo pedido";
    public static final String CREATE_ORDER_DESCRIPTION = "Cria um novo pedido com base nos dados enviados.";
    public static final String CREATE_ORDER_201 = "Pedido criado com sucesso";

    public static final String UPDATE_ORDER_SUMMARY = "Atualizar um pedido existente";
    public static final String UPDATE_ORDER_DESCRIPTION = "Atualiza um pedido específico com os novos dados enviados.";
    public static final String UPDATE_ORDER_204 = "Pedido atualizado com sucesso";

    public static final String FIND_ORDER_SUMMARY = "Buscar pedido por ID";
    public static final String FIND_ORDER_DESCRIPTION = "Retorna os detalhes de um pedido específico pelo ID.";
    public static final String FIND_ORDER_200 = "Pedido encontrado com sucesso";

    public static final String FIND_ORDERS_BY_STATUS_SUMMARY = "Buscar pedidos por status";
    public static final String FIND_ORDERS_BY_STATUS_DESCRIPTION = "Retorna uma lista de pedidos com um status específico.";
    public static final String FIND_ORDERS_BY_STATUS_200 = "Pedidos encontrados com sucesso";
    public static final String FIND_ORDERS_BY_STATUS_400 = "Status inválido";

    private SwaggerConstants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
