package com.rostami.onlinehomeservices.exception;

public class InvalidCaptchaException extends RuntimeException{
    public InvalidCaptchaException() {
        super();
    }

    public InvalidCaptchaException(String message) {
        super(message);
    }

    public InvalidCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
