package com.rostami.onlineservice.dto.out;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateUpdateResult {
    private Long id;
    private Boolean success;
}