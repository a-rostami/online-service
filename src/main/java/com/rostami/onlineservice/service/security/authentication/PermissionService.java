package com.rostami.onlineservice.service.security.authentication;

import com.rostami.onlineservice.dto.out.security.PermissionFindResult;
import com.rostami.onlineservice.model.security.authentication.Permission;
import com.rostami.onlineservice.model.security.enums.PermissionEnum;
import com.rostami.onlineservice.repository.security.PermissionRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class PermissionService extends BaseService<Permission, Long, PermissionFindResult> {
    private final PermissionRepository repository;

    @PostConstruct
    public void init() {
        setRepository(repository);
        setBaseOutDto(PermissionFindResult.builder().build());
    }

    public Permission findByPermissionEnum(PermissionEnum permissionEnum){
        return repository.findByPermissionEnum(permissionEnum);
    }
}
