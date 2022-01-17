package com.rostami.onlineservice.dto.in.update;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
public class OpinionUpdateParam {
    @Max(value = 5)
    @Min(value = 1)
    private Integer rate;
}
