package com.geosapiens.eucomida.exception;

import static com.geosapiens.eucomida.constant.ErrorMessages.ORDER_NOT_FOUND;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super(ORDER_NOT_FOUND);
    }
}
