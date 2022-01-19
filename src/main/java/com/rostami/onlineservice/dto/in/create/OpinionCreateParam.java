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
    // id can be null when there is no relation
    private Long id;
    @Max(value = 5)
    @Min(value = 1)
    private Integer rate;
    private String description;
    private AdCreateParam adCreateParam;
    private ExpertCreateParam expertCreateParam;


    @Override
    public Opinion convertToDomain() {
        if (expertCreateParam == null || expertCreateParam.getId() == null)
            throw new EntityRelationException("Opinion Should have Expert!");
        if (adCreateParam == null || adCreateParam.getId() == null)
            throw new EntityRelationException("Opinion Should have Ad!");

        return Opinion.builder()
                .description(description)
                .rate(rate)
                .ad(Ad.builder().id(adCreateParam.getId()).build())
                .expert(Expert.builder().id(expertCreateParam.getId()).build())
                .build();
    }
}
