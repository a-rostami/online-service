package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.out.single.AdminFindResult;
import com.rostami.onlineservice.model.Admin;
import com.rostami.onlineservice.repository.AdminRepository;
import com.rostami.onlineservice.service.base.UserService;
import com.rostami.onlineservice.service.registration.EmailTokenService;
import com.rostami.onlineservice.service.registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AdminService extends UserService<Admin, Long, AdminFindResult> {
    private final AdminRepository repository;
    private final RegistrationService registrationService;
    private final EmailTokenService emailTokenService;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(AdminFindResult.builder().build());
        setRegistrationService(registrationService);
        setEmailTokenService(emailTokenService);
    }
}
