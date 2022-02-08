package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.dto.in.BaseUpdateDto;
import com.rostami.onlineservice.model.MainServ;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainServUpdateParam implements BaseUpdateDto<MainServ> {
    @NotNull
    private Long id;
    @NotNull
    private String name;


    @Override
    public MainServ convertToDomain(MainServ fetchedEntity) {
        fetchedEntity.setId(id);
        fetchedEntity.setName(name);
        return fetchedEntity;
    }
}
