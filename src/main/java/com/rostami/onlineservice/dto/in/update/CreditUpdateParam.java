package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Credit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CreditUpdateParam implements BaseInDto<Credit> {
    @NotNull
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
