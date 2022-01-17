package com.rostami.onlineservice.dto.in.create;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter
@Getter
@Builder
public class OpinionCreateParam {
    @Max(value = 5)
    @Min(value = 1)
    private Integer rate;
    private String description;
}
