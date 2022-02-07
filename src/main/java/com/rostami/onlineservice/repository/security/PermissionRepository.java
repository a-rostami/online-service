package com.rostami.onlineservice.repository.security;

import com.rostami.onlineservice.model.security.authentication.Permission;
import com.rostami.onlineservice.model.security.enums.PermissionEnum;
import com.rostami.onlineservice.repository.base.BaseRepository;

public interface PermissionRepository extends BaseRepository<Permission, Long> {
    Permission findByPermissionEnum(PermissionEnum permissionEnum);
}
