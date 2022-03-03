package com.rostami.onlinehomeservices.dto.out.single;

import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.model.Opinion;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpinionFindResult implements BaseOutDto<Opinion, OpinionFindResult> {
    private Long id;
    @Max(value = 5)
    @Min(value = 1)
    private Integer rate;
    private String description;


    @Override
    public OpinionFindResult convertToDto(Opinion entity) {
        id = entity.getId();
        rate = entity.getRate();
        description = entity.getDescription();
        return this;
    }
}
