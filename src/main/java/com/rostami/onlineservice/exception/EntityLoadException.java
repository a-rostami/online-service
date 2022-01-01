package com.rostami.onlineservice.exception;

public class EntityLoadException extends RuntimeException{
    public EntityLoadException() {
        super();
    }

    public EntityLoadException(String message) {
        super(message);
    }
}
