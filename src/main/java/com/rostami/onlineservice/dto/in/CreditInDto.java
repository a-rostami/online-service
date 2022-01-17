package com.rostami.onlineservice.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CreditInDto {
    private BigDecimal balance;
}
