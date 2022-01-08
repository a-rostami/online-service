package com.rostami.onlineservice.exception;

public class DuplicatedEmailException extends RuntimeException{
    public DuplicatedEmailException() {
        super();
    }

    public DuplicatedEmailException(String message) {
        super(message);
    }
}
