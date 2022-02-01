package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.Credit;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditUpdateParam implements BaseInDto<Credit> {
    @NotNull
    private Long id;
    @NotNull
    private BigDecimal balance;

    @Override
    public Credit convertToDomain() {
        return Credit.builder()
                .id(id)
                .balance(balance)
                .build();
    }
}
