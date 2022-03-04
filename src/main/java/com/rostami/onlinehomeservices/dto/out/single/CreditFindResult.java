package com.rostami.onlinehomeservices.dto.out.single;

import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.model.Credit;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditFindResult implements BaseOutDto<Credit, CreditFindResult> {
    private Long id;
    private BigDecimal balance;

    @Override
    public CreditFindResult convertToDto(Credit entity) {
        id = entity.getId();
        balance = entity.getBalance();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditFindResult that = (CreditFindResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
