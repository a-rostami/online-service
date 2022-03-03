package com.rostami.onlinehomeservices.dto.in.update;

import com.rostami.onlinehomeservices.dto.in.BaseUpdateDto;
import com.rostami.onlinehomeservices.model.Credit;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditUpdateParam implements BaseUpdateDto<Credit> {
    @NotNull
    private Long id;
    @NotNull
    private BigDecimal balance;

    @Override
    public Credit convertToDomain(Credit fetchedEntity) {
        fetchedEntity.setId(id);
        fetchedEntity.setBalance(balance);
        return fetchedEntity;
    }
}
