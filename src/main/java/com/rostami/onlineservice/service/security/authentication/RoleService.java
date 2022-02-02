package com.rostami.onlineservice.service.security.authentication;

import com.rostami.onlineservice.dto.out.security.RoleFindResult;
import com.rostami.onlineservice.model.security.authentication.Role;
import com.rostami.onlineservice.model.security.enums.RoleEnum;
import com.rostami.onlineservice.repository.RoleRepository;
import com.rostami.onlineservice.service.base.BaseService;
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

    public Role findByName(RoleEnum roleEnum){
        return repository.findByName(roleEnum.getName());
    }
}
