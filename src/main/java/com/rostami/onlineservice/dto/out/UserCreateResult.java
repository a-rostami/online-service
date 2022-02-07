package com.rostami.onlineservice.dto.out;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserCreateResult {
    private Long id;
    private Boolean success;
    private String token;
}
