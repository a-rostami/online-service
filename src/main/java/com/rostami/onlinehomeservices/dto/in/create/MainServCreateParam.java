package com.rostami.onlinehomeservices.dto.in.create;

import com.rostami.onlinehomeservices.dto.in.BaseInDto;
import com.rostami.onlinehomeservices.model.MainServ;
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
