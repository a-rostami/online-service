package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.out.single.AdminFindResult;
import com.rostami.onlineservice.model.Admin;
import com.rostami.onlineservice.repository.AdminRepository;
import com.rostami.onlineservice.service.base.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AdminService extends UserService<Admin, Long, AdminFindResult> {
    private final AdminRepository repository;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(AdminFindResult.builder().build());
    }
}
