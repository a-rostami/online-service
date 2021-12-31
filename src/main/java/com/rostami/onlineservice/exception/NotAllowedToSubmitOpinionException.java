package com.rostami.onlineservice.exception;

public class NotAllowedToSubmitOpinionException extends RuntimeException{
    public NotAllowedToSubmitOpinionException() {
        super();
    }

    public NotAllowedToSubmitOpinionException(String message) {
        super(message);
    }
}
