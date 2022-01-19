package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.SubServ;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubServUpdateParam implements BaseInDto<SubServ> {
    @NotNull
    private Long id;
    private String name;
    private BigDecimal basePrice;

    @Override
    public SubServ convertToDomain() {
        return SubServ.builder()
                .id(id)
                .name(name)
                .basePrice(basePrice)
                .build();
    }
}
