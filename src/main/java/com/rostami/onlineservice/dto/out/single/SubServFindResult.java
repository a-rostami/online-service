package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.entity.SubServ;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubServFindResult implements BaseOutDto<SubServ, SubServFindResult> {
    private Long id;
    private String name;
    private BigDecimal basePrice;
    private MainServFindResult mainServFindResult;

    @Override
    public SubServFindResult convertToDto(SubServ entity) {
        id = entity.getId();
        name = entity.getName();
        basePrice = entity.getBasePrice();
        mainServFindResult = mainServFindResult.convertToDto(entity.getMainServ());
        return this;
    }
}
