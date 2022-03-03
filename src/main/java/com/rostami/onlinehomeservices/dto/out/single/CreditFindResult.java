package com.rostami.onlinehomeservices.dto.out.single;

import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.model.Credit;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditFindResult implements BaseOutDto<Credit, CreditFindResult> {
    private Long id;
    private BigDecimal balance;

    @Override
    public CreditFindResult convertToDto(Credit entity) {
        id = entity.getId();
        balance = entity.getBalance();
        return this;
    }
}
