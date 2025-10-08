package com.geosapiens.eucomida.exception;

import static com.geosapiens.eucomida.constant.ErrorMessages.COURIER_NOT_FOUND;

public class CourierNotFoundException extends RuntimeException {
    public CourierNotFoundException() {
        super(COURIER_NOT_FOUND);
    }
}
