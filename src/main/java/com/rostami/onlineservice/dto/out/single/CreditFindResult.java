package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.entity.Credit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CreditFindResult implements BaseOutDto<Credit, CreditFindResult> {
    private BigDecimal balance;

    @Override
    public CreditFindResult convertToDto(Credit entity) {
        balance = entity.getBalance();
        return this;
    }
}
