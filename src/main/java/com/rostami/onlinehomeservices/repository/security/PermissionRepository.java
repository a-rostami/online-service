package com.rostami.onlinehomeservices.repository.security;

import com.rostami.onlinehomeservices.model.security.auth.Permission;
import com.rostami.onlinehomeservices.model.security.enums.PermissionEnum;
import com.rostami.onlinehomeservices.repository.base.BaseRepository;

import java.util.Optional;

public interface PermissionRepository extends BaseRepository<Permission, Long> {
    Optional<Permission> findByPermissionEnum(PermissionEnum permissionEnum);
}
