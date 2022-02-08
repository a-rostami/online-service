package com.rostami.onlineservice.exception;

public class WrongPreviousPasswordException extends RuntimeException{
    public WrongPreviousPasswordException() {
        super();
    }

    public WrongPreviousPasswordException(String message) {
        super(message);
    }

    public WrongPreviousPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
