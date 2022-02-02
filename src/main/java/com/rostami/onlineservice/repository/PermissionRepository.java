package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.model.security.authentication.Permission;
import com.rostami.onlineservice.repository.base.BaseRepository;

public interface PermissionRepository extends BaseRepository<Permission, Long> {
    Permission findByName(String name);
}