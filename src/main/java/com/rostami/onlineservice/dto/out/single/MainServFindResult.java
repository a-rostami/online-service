package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.entity.MainServ;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MainServFindResult implements BaseOutDto<MainServ, MainServFindResult> {
    private String name;

    @Override
    public MainServFindResult convertToDto(MainServ entity) {
        name = entity.getName();
        return this;
    }
}