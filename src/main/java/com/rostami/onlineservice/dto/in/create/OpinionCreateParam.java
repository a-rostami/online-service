package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.Opinion;
import com.rostami.onlineservice.exception.EntityRelationException;
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
