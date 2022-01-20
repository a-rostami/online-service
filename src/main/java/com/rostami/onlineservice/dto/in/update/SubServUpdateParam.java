package com.rostami.onlineservice.dto.in.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.MainServ;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal basePrice;
    private MainServUpdateParam mainServUpdateParam;

    @Override
    public SubServ convertToDomain() {
        return SubServ.builder()
                .id(id)
                .name(name)
                .basePrice(basePrice)
                .mainServ(MainServ.builder().id(mainServUpdateParam.getId())
                        .name(mainServUpdateParam.getName())
                        .build())
                .build();
    }
}
