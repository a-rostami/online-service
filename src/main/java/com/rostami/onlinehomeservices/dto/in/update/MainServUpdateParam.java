package com.rostami.onlinehomeservices.dto.in.update;

import com.rostami.onlinehomeservices.dto.in.BaseUpdateDto;
import com.rostami.onlinehomeservices.model.MainServ;
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
