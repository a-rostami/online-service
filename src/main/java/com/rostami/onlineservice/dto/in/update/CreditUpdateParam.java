package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseDto;
import com.rostami.onlineservice.entity.Credit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CreditUpdateParam implements BaseDto<Credit> {
    private Long id;
    private BigDecimal balance;

    @Override
    public Credit convertToDomain() {
        return Credit.builder()
                .id(id)
                .balance(balance)
                .build();
    }
}
