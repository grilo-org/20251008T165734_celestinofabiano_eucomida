package com.geosapiens.eucomida.exception;

import static com.geosapiens.eucomida.constant.ErrorMessages.AUTHENTICATED_USER_NOT_FOUND;

public class AuthenticatedUserNotFoundException extends RuntimeException {
    public AuthenticatedUserNotFoundException() {
        super(AUTHENTICATED_USER_NOT_FOUND);
    }
}
