package com.rostami.onlinehomeservices.exception;

public class NotAllowedToSubmitOpinionException extends RuntimeException{
    public NotAllowedToSubmitOpinionException() {
        super();
    }

    public NotAllowedToSubmitOpinionException(String message) {
        super(message);
    }
}
