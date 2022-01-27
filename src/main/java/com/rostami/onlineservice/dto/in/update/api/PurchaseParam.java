package com.rostami.onlineservice.dto.in.update.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseParam {
    private Long customerId;
    private Long expertId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;
}
