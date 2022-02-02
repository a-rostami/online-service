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
public class PermissionFindResult implements BaseOutDto<Permission, PermissionFindResult> {
    private Long id;
    private String name;
    private Set<Role> roles;

    @Override
    public PermissionFindResult convertToDto(Permission entity) {
        return PermissionFindResult.builder()
                .id(entity.getId())
                .name(entity.getName())
                .roles(entity.getRoles())
                .build();
    }
}
