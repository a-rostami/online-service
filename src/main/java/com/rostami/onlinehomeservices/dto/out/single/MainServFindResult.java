package com.rostami.onlinehomeservices.dto.out.single;

import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.model.MainServ;
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
