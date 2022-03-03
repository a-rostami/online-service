package com.rostami.onlinehomeservices.repository.security;

import com.rostami.onlinehomeservices.model.security.auth.Permission;
import com.rostami.onlinehomeservices.model.security.enums.PermissionEnum;
import com.rostami.onlinehomeservices.repository.base.BaseRepository;

public interface PermissionRepository extends BaseRepository<Permission, Long> {
    Permission findByPermissionEnum(PermissionEnum permissionEnum);
}
