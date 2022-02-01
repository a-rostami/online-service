package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.MainServ;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainServCreateParam implements BaseInDto<MainServ> {
    private String name;

    @Override
    public MainServ convertToDomain() {
        return MainServ.builder()
                .name(name)
                .build();
    }
}
