package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.MainServ;
import com.rostami.onlineservice.entity.SubServ;
import com.rostami.onlineservice.exception.EntityRelationException;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubServCreateParam implements BaseInDto<SubServ> {
    // id can be null when there is no relation
    private Long id;
    private String name;
    private BigDecimal basePrice;
    private MainServCreateParam mainServCreateParam;

    @Override
    public SubServ convertToDomain() {
        if (mainServCreateParam == null || mainServCreateParam.getId() == null)
            throw new EntityRelationException("SubServ Should have Main Serv!");

        return SubServ.builder()
                .basePrice(basePrice)
                .name(name)
                .mainServ(MainServ.builder().id(mainServCreateParam.getId()).build())
                .build();
    }
}
