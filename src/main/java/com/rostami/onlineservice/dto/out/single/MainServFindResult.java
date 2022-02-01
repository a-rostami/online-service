package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.model.MainServ;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainServFindResult implements BaseOutDto<MainServ, MainServFindResult> {
    private Long id;
    private String name;

    @Override
    public MainServFindResult convertToDto(MainServ entity) {
        id = entity.getId();
        name = entity.getName();
        return this;
    }
}
