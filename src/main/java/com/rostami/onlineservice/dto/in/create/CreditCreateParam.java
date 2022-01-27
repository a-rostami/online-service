package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Credit;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.base.User;
import com.rostami.onlineservice.entity.enums.Role;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCreateParam implements BaseInDto<Credit> {
    private BigDecimal balance;

    @Override
    public Credit convertToDomain() {

        return Credit.builder()
                .balance(balance)
                .build();
    }
}
