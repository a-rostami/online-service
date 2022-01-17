package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseDto;
import com.rostami.onlineservice.entity.Opinion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter
@Getter
@Builder
public class OpinionCreateParam implements BaseDto<Opinion> {
    @Max(value = 5)
    @Min(value = 1)
    private Integer rate;
    private String description;
    private AdCreateParam adCreateParam;
    private ExpertCreateParam expertCreateParam;


    @Override
    public Opinion convertToDomain() {
        return Opinion.builder()
                .description(description)
                .rate(rate)
                .ad(adCreateParam.convertToDomain())
                .expert(expertCreateParam.convertToDomain())
                .build();
    }
}
