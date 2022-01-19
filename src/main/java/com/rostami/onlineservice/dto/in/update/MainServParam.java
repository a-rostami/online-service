package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.MainServ;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainServParam implements BaseInDto<MainServ> {
    @NotNull
    private Long id;
    private String name;


    @Override
    public MainServ convertToDomain() {
        return MainServ.builder()
                .id(id)
                .name(name)
                .build();
    }
}
