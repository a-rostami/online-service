package com.rostami.onlinehomeservices.dto.in.create;

import com.rostami.onlinehomeservices.dto.in.BaseInDto;
import com.rostami.onlinehomeservices.model.Ad;
import com.rostami.onlinehomeservices.model.Expert;
import com.rostami.onlinehomeservices.model.Opinion;
import com.rostami.onlinehomeservices.exception.EntityRelationException;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpinionCreateParam implements BaseInDto<Opinion> {
    @Max(value = 5)
    @Min(value = 1)
    private Integer rate;
    private String description;
    private Long adId;
    private Long expertId;


    @Override
    public Opinion convertToDomain() {
        if (expertId == null)
            throw new EntityRelationException("Opinion Should have Expert!");
        if (adId == null)
            throw new EntityRelationException("Opinion Should have Ad!");

        return Opinion.builder()
                .description(description)
                .rate(rate)
                .ad(Ad.builder().id(adId).build())
                .expert(Expert.builder().id(expertId).build())
                .build();
    }
}
