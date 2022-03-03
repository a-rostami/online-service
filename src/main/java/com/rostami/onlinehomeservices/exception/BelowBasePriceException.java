package com.rostami.onlinehomeservices.exception;

public class BelowBasePriceException extends RuntimeException{
    public BelowBasePriceException() {
        super();
    }

    public BelowBasePriceException(String message) {
        super(message);
    }
}
