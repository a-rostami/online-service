package com.rostami.onlinehomeservices.dto.in.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rostami.onlinehomeservices.dto.in.BaseUpdateDto;
import com.rostami.onlinehomeservices.model.SubServ;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubServUpdateParam implements BaseUpdateDto<SubServ> {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal basePrice;

    @Override
    public SubServ convertToDomain(SubServ fetchedEntity) {
        fetchedEntity.setId(id);
        fetchedEntity.setName(name);
        fetchedEntity.setBasePrice(basePrice);
        return fetchedEntity;
    }
}
