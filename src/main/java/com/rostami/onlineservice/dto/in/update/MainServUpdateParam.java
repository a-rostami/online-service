package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.MainServ;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainServUpdateParam implements BaseInDto<MainServ> {
    @NotNull
    private Long id;
    @NotNull
    private String name;


    @Override
    public MainServ convertToDomain() {
        return MainServ.builder()
                .id(id)
                .name(name)
                .build();
    }
}
