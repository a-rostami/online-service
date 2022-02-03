package com.rostami.onlineservice.dto.out.security;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.model.security.authentication.Permission;
import com.rostami.onlineservice.model.security.authentication.Role;
import com.rostami.onlineservice.model.security.enums.RoleEnum;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleFindResult implements BaseOutDto<Role, RoleFindResult> {
    private Long id;
    private RoleEnum roleEnum;
    private Set<Permission> permissions;

    @Override
    public RoleFindResult convertToDto(Role entity) {
        return RoleFindResult.builder()
                .id(entity.getId())
                .roleEnum(entity.getRoleEnum())
                .permissions(entity.getPermissions())
                .build();
    }
}
