package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Opinion;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpinionUpdateParam implements BaseInDto<Opinion> {
    @NotNull
    private Long id;
    @Max(value = 5)
    @Min(value = 1)
    @NotNull
    private Integer rate;

    @Override
    public Opinion convertToDomain() {
        return Opinion.builder()
                .id(id)
                .rate(rate)
                .build();
    }
}
