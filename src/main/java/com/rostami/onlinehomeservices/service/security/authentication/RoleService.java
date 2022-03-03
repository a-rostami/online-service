package com.rostami.onlinehomeservices.service.security.authentication;

import com.rostami.onlinehomeservices.dto.out.security.RoleFindResult;
import com.rostami.onlinehomeservices.model.security.auth.Role;
import com.rostami.onlinehomeservices.model.security.enums.RoleEnum;
import com.rostami.onlinehomeservices.repository.security.RoleRepository;
import com.rostami.onlinehomeservices.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class RoleService extends BaseService<Role, Long, RoleFindResult> {
    private final RoleRepository repository;

    @PostConstruct
    public void init() {
        setRepository(repository);
        setBaseOutDto(RoleFindResult.builder().build());
    }

    public Role findByRoleEnum(RoleEnum roleEnum){
        return repository.findByRoleEnum(roleEnum);
    }
}
