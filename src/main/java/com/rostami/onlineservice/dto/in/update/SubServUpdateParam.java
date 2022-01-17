package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseDto;
import com.rostami.onlineservice.entity.SubServ;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SubServUpdateParam implements BaseDto<SubServ> {
    private Long id;
    private String name;
    private BigDecimal basePrice;

    @Override
    public SubServ convertToDomain() {
        return SubServ.builder()
                .id(id)
                .name(name)
                .basePrice(basePrice)
                .build();
    }
}
