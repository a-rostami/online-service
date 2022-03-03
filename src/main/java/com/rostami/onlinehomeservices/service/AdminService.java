package com.rostami.onlinehomeservices.service;

import com.rostami.onlinehomeservices.dto.in.update.api.PasswordUpdateParam;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.AdminFindResult;
import com.rostami.onlinehomeservices.exception.EntityLoadException;
import com.rostami.onlinehomeservices.exception.WrongPreviousPasswordException;
import com.rostami.onlinehomeservices.model.Admin;
import com.rostami.onlinehomeservices.repository.AdminRepository;
import com.rostami.onlinehomeservices.service.base.UserService;
import com.rostami.onlinehomeservices.service.registration.EmailTokenService;
import com.rostami.onlinehomeservices.service.registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.ENTITY_ID_LOAD_MESSAGE;
import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.WRONG_PREVIOUS_PASSWORD_MESSAGE;

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
                .orElseThrow(() -> new EntityLoadException(ENTITY_ID_LOAD_MESSAGE));

        String adminPassword = admin.getPassword();
        if (!bCryptPasswordEncoder.matches(param.getPreviousPassword(), adminPassword))
            throw new WrongPreviousPasswordException(WRONG_PREVIOUS_PASSWORD_MESSAGE);

        admin.setPassword(param.getNewPassword());
        Admin saved = repository.save(admin);

        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }
}
