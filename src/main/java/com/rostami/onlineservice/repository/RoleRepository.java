package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.model.security.authentication.Role;
import com.rostami.onlineservice.repository.base.BaseRepository;

public interface RoleRepository extends BaseRepository<Role, Long> {
    Role findByName(String name);
}
