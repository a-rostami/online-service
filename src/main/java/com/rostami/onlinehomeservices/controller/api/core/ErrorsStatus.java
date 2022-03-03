package com.rostami.onlinehomeservices.controller.api.core;

public enum ErrorsStatus {

    SUCCESS(0, "SUCCESS"),
    NOT_FOUND(404, "NOT_FOUND"),
    UNKNOWN_ERROR(1, "UNKNOWN ERROR");
    private final int code;
    private final String message;

    ErrorsStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
