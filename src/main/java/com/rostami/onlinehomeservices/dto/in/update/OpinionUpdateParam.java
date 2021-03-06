package com.rostami.onlinehomeservices.dto.in.update;

import com.rostami.onlinehomeservices.dto.in.BaseUpdateDto;
import com.rostami.onlinehomeservices.model.Opinion;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpinionUpdateParam implements BaseUpdateDto<Opinion> {
    @NotNull
    private Long id;
    @Max(value = 5)
    @Min(value = 1)
    @NotNull
    private Integer rate;

    @Override
    public Opinion convertToDomain(Opinion fetchedEntity) {
        fetchedEntity.setId(id);
        fetchedEntity.setRate(rate);
        return fetchedEntity;
    }
}
