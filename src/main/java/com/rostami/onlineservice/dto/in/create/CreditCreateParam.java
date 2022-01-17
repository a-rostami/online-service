package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseDto;
import com.rostami.onlineservice.entity.Credit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CreditCreateParam implements BaseDto<Credit> {
    private BigDecimal balance;

    @Override
    public Credit convertToDomain() {
        return Credit.builder()
                .balance(balance)
                .build();
    }
}
