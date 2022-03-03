package com.rostami.onlinehomeservices.exception;

public class NotEnoughCreditBalanceException extends RuntimeException{
    public NotEnoughCreditBalanceException() {
        super();
    }

    public NotEnoughCreditBalanceException(String message) {
        super(message);
    }

    public NotEnoughCreditBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
