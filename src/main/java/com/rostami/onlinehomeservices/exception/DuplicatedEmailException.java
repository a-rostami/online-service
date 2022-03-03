package com.rostami.onlinehomeservices.exception;

public class DuplicatedEmailException extends RuntimeException{
    public DuplicatedEmailException() {
        super();
    }

    public DuplicatedEmailException(String message) {
        super(message);
    }
}
