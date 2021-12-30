package com.rostami.onlineservice.exception;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException() {
        super();
    }

    public DuplicateEmailException(String message) {
        super(message);
    }
}
