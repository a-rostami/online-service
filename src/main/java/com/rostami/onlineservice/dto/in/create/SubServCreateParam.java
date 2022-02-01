package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.MainServ;
import com.rostami.onlineservice.model.SubServ;
import com.rostami.onlineservice.exception.EntityRelationException;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubServCreateParam implements BaseInDto<SubServ> {
    private String name;
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
