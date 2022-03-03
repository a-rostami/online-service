package com.rostami.onlinehomeservices.dto.in.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rostami.onlinehomeservices.dto.in.BaseInDto;
import com.rostami.onlinehomeservices.model.MainServ;
import com.rostami.onlinehomeservices.model.SubServ;
import com.rostami.onlinehomeservices.exception.EntityRelationException;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubServCreateParam implements BaseInDto<SubServ> {
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal basePrice;
    private Long mainServId;

    @Override
    public SubServ convertToDomain() {
        if (mainServId == null)
            throw new EntityRelationException("SubServ Should have Main Serv!");

        return SubServ.builder()
                .basePrice(basePrice)
                .name(name)
                .mainServ(MainServ.builder().id(mainServId).build())
                .build();
    }
}
