package com.rostami.onlineservice.dto.in.update.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositParam {
    private Long userId;
    @JsonFormat
    private BigDecimal amount;
}
