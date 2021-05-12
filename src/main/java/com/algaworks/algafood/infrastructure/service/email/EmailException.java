package com.algaworks.algafood.infrastructure.service.email;

public class EmailException extends RuntimeException {

    private static final long serialVersionUID = 7592435867476757995L;

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailException(String message) {
        super(message);
    }

}
