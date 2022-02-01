package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.Credit;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCreateParam implements BaseInDto<Credit> {
    private BigDecimal balance;

    @Override
    public Credit convertToDomain() {

        return Credit.builder()
                .balance(balance)
                .build();
    }
}
