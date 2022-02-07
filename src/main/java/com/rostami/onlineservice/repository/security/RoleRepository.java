package com.rostami.onlineservice.repository.security;

import com.rostami.onlineservice.model.security.authentication.Role;
import com.rostami.onlineservice.model.security.enums.RoleEnum;
import com.rostami.onlineservice.repository.base.BaseRepository;

public interface
RoleRepository extends BaseRepository<Role, Long> {
    Role findByRoleEnum(RoleEnum roleEnum);
}
