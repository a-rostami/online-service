package com.rostami.onlineservice.dto.in.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.MainServ;
import com.rostami.onlineservice.model.SubServ;
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
    @NotNull
    private String name;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal basePrice;
    @NotNull
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
