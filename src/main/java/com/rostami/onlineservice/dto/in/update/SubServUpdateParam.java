package com.rostami.onlineservice.dto.in.update;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SubServUpdateParam {
    private String name;
    private BigDecimal basePrice;
}
