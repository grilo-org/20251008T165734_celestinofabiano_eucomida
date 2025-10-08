package com.geosapiens.eucomida.constant;

public class ErrorMessages {

    public static final String UTILITY_CLASS = "Esta é uma classe utilitária e não pode ser instanciada";
    public static final String AUTHENTICATED_USER_NOT_FOUND = "Usuário autenticado não encontrado";
    public static final String USER_NOT_FOUND = "Usuário não encontrado no banco de dados";
    public static final String ORDER_NOT_FOUND = "Pedido não encontrado";
    public static final String COURIER_NOT_FOUND = "Entregador não encontrado";
    public static final String RESOURCE_NOT_FOUND_ERROR = "Endpoint não encontrado no servidor";
    public static final String NOT_READABLE_ERROR = "Não foi possível mapear os parâmetros enviados. Verifique se o conteúdo e a URL da requisição estã ocorretos";
    public static final String VALIDATION_ERROR = "Erro de validação";
    public static final String INTERNAL_SERVER_ERROR = "Erro interno do servidor";


    private ErrorMessages() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
