package com.rostami.onlinehomeservices.repository.security;

import com.rostami.onlinehomeservices.model.security.auth.Role;
import com.rostami.onlinehomeservices.model.security.enums.RoleEnum;
import com.rostami.onlinehomeservices.repository.base.BaseRepository;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<Role, Long> {
    Optional<Role> findByRoleEnum(RoleEnum roleEnum);
}
