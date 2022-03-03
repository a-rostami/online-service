package com.rostami.onlinehomeservices.exception;

public class EmailConfirmationExpiredException extends RuntimeException{
    public EmailConfirmationExpiredException() {
        super();
    }

    public EmailConfirmationExpiredException(String message) {
        super(message);
    }

    public EmailConfirmationExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
