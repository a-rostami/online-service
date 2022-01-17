package com.rostami.onlineservice.dto.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseResult<T> {
    private int code;
    private String message;
    private T data;
}
