package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.in.update.api.PasswordUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.AdminFindResult;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.exception.WrongPreviousPasswordException;
import com.rostami.onlineservice.model.Admin;
import com.rostami.onlineservice.repository.AdminRepository;
import com.rostami.onlineservice.service.base.UserService;
import com.rostami.onlineservice.service.registration.EmailTokenService;
import com.rostami.onlineservice.service.registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AdminService extends UserService<Admin, Long, AdminFindResult> {
    private final AdminRepository repository;
    private final RegistrationService registrationService;
    private final EmailTokenService emailTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(AdminFindResult.builder().build());
        setRegistrationService(registrationService);
        setEmailTokenService(emailTokenService);
    }

    @Transactional
    public CreateUpdateResult changePassword(PasswordUpdateParam param){
        Admin admin = repository.findByEmail(param.getEmail())
                .orElseThrow(() -> new EntityLoadException("There Is No Admin With This Email"));

        String adminPassword = admin.getPassword();
        if (!bCryptPasswordEncoder.matches(param.getPreviousPassword(), adminPassword))
            throw new WrongPreviousPasswordException("Previous Password Is Wrong !");

        admin.setPassword(param.getNewPassword());
        Admin saved = repository.save(admin);

        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }
}
