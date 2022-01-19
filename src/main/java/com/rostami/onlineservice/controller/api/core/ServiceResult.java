package com.rostami.onlineservice.controller.api.core;

import com.rostami.onlineservice.controller.api.errors.api.ApiError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResult<T> {

    private int code;
    private T data;
    private String message;
    private ApiError error;


    public static <T> ServiceResult<T> success(T data) {
        return new ServiceResult<>(ErrorsStatus.SUCCESS.getCode(),
                data,
                ErrorsStatus.SUCCESS.getMessage(),
                null);
    }

    public static <T> ServiceResult<T> success() {
        return new ServiceResult<>(ErrorsStatus.SUCCESS.getCode(),
                null,
                ErrorsStatus.SUCCESS.getMessage(),
                null);
    }

    public static <T> ServiceResult<T> notFound() {
        return new ServiceResult<>(ErrorsStatus.NOT_FOUND.getCode(),
                null,
                ErrorsStatus.NOT_FOUND.getMessage(),
                null);
    }

    public static <T> ServiceResult<T> fail(HttpStatus httpStatus, ErrorsStatus es) {
        return new ServiceResult<>(es.getCode(),
                null,
                es.getMessage(),
                new ApiError(httpStatus, es.getMessage()));
    }

    public static ServiceResult<Void> fail(ApiError apiError) {
        return new ServiceResult<>(apiError.getStatus().value(),
                null,
                apiError.getMessage(),
                apiError);
    }
}
