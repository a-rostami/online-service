package com.rostami.onlineservice.exception;

public class EntityRelationException extends RuntimeException{
    public EntityRelationException() {
        super();
    }

    public EntityRelationException(String message) {
        super(message);
    }

    public EntityRelationException(String message, Throwable cause) {
        super(message, cause);
    }
}
