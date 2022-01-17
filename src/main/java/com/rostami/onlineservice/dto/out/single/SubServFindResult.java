package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.entity.SubServ;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SubServFindResult implements BaseOutDto<SubServ, SubServFindResult> {
    private Long id;
    private String name;
    private BigDecimal basePrice;

    @Override
    public SubServFindResult convertToDto(SubServ entity) {
        id = entity.getId();
        name = entity.getName();
        basePrice = entity.getBasePrice();
        return this;
    }
}
