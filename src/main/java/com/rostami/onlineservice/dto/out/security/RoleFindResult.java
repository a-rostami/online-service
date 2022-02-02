package com.rostami.onlineservice.dto.out.security;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.model.security.authentication.Permission;
import com.rostami.onlineservice.model.security.authentication.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleFindResult implements BaseOutDto<Role, RoleFindResult> {
    private Long id;
    private String name;
    private Set<Permission> permissions;

    @Override
    public RoleFindResult convertToDto(Role entity) {
        return RoleFindResult.builder()
                .id(entity.getId())
                .name(entity.getName())
                .permissions(entity.getPermissions())
                .build();
    }
}