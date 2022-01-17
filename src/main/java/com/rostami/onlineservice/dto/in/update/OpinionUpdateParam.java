package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Opinion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
public class OpinionUpdateParam implements BaseInDto<Opinion> {
    private Long id;
    @Max(value = 5)
    @Min(value = 1)
    private Integer rate;

    @Override
    public Opinion convertToDomain() {
        return Opinion.builder()
                .id(id)
                .rate(rate)
                .build();
    }
}
