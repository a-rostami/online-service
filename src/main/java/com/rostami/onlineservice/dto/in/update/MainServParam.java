package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseDto;
import com.rostami.onlineservice.entity.MainServ;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MainServParam implements BaseDto<MainServ> {
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
