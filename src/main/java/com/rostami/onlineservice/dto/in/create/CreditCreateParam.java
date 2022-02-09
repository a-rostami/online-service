package com.rostami.onlineservice.dto.in.create;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal balance;

    @Override
    public Credit convertToDomain() {

        return Credit.builder()
                .balance(balance)
                .build();
    }
}
