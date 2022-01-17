package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseDto;
import com.rostami.onlineservice.entity.SubServ;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SubServCreateParam implements BaseDto<SubServ> {
    private String name;
    private BigDecimal basePrice;
    private MainServCreateParam mainServCreateParam;

    @Override
    public SubServ convertToDomain() {
        return SubServ.builder()
                .basePrice(basePrice)
                .name(name)
                .mainServ(mainServCreateParam.convertToDomain())
                .build();
    }
}
