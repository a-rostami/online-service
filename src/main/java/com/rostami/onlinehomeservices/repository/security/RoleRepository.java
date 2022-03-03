package com.rostami.onlinehomeservices.repository.security;

import com.rostami.onlinehomeservices.model.security.auth.Role;
import com.rostami.onlinehomeservices.model.security.enums.RoleEnum;
import com.rostami.onlinehomeservices.repository.base.BaseRepository;

public interface
RoleRepository extends BaseRepository<Role, Long> {
    Role findByRoleEnum(RoleEnum roleEnum);
}
