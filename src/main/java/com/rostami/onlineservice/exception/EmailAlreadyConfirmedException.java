package com.rostami.onlineservice.exception;

public class EmailAlreadyConfirmedException extends RuntimeException{
    public EmailAlreadyConfirmedException() {
        super();
    }

    public EmailAlreadyConfirmedException(String message) {
        super(message);
    }

    public EmailAlreadyConfirmedException(String message, Throwable cause) {
        super(message, cause);
    }
}
