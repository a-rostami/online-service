package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.MainServ;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MainServCreateParam implements BaseInDto<MainServ> {
    private String name;

    @Override
    public MainServ convertToDomain() {
        return MainServ.builder()
                .name(name)
                .build();
    }
}
