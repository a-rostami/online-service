package com.rostami.onlineservice.dto.in.create;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CreditCreateParam {
    private BigDecimal balance;
}
