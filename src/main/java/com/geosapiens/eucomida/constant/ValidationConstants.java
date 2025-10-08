package com.geosapiens.eucomida.constant;

import static com.geosapiens.eucomida.constant.ErrorMessages.UTILITY_CLASS;

public class ValidationConstants {

    public static final String COURIER_VEHICLE_TYPE_REQUIRED = "O tipo de veículo é obrigatório";
    public static final String COURIER_PLATE_NUMBER_REGEX = "^[A-Z]{3}\\d[A-Z]\\d{2}$";
    public static final String COURIER_PLATE_NUMBER_MESSAGE = "A placa deve estar no formato correto (ex: AAA1B23)";

    public static final String ORDER_USER_REQUIRED = "O pedido deve estar associado a um usuário";
    public static final String ORDER_STATUS_REQUIRED = "O status do pedido é obrigatório";
    public static final String ORDER_PAYMENT_STATUS_REQUIRED = "O status do pagamento é obrigatório";
    public static final String ORDER_TOTAL_PRICE_REQUIRED = "O preço total é obrigatório";
    public static final String ORDER_TOTAL_PRICE_POSITIVE = "O preço total deve ser maior que zero";

    public static final String USER_NAME_REQUIRED = "O nome do usuário não pode estar em branco";
    public static final String USER_EMAIL_REQUIRED = "O e-mail do usuário não pode estar em branco";
    public static final String USER_EMAIL_INVALID = "O e-mail do usuário deve ser válido";

    private ValidationConstants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

}
