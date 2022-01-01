package com.rostami.onlineservice.exception;

public class BelowBasePriceException extends RuntimeException{
    public BelowBasePriceException() {
        super();
    }

    public BelowBasePriceException(String message) {
        super(message);
    }
}
