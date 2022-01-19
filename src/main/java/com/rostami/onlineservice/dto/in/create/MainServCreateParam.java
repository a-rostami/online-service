package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.MainServ;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainServCreateParam implements BaseInDto<MainServ> {
    // id can be null when there is no relation
    private Long id;
    private String name;

    @Override
    public MainServ convertToDomain() {
        return MainServ.builder()
                .name(name)
                .build();
    }
}
