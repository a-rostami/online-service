package com.rostami.onlinehomeservices.service.security.authentication;

import com.rostami.onlinehomeservices.dto.out.security.PermissionFindResult;
import com.rostami.onlinehomeservices.model.security.auth.Permission;
import com.rostami.onlinehomeservices.model.security.enums.PermissionEnum;
import com.rostami.onlinehomeservices.repository.security.PermissionRepository;
import com.rostami.onlinehomeservices.service.base.BaseService;
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
