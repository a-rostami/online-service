package com.rostami.onlineservice.service.base;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.CreditFindResult;
import com.rostami.onlineservice.model.Credit;
import com.rostami.onlineservice.model.base.User;
import com.rostami.onlineservice.exception.DuplicatedEmailException;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.model.registration.EmailToken;
import com.rostami.onlineservice.model.security.authentication.Role;
import com.rostami.onlineservice.service.registration.EmailTokenService;
import com.rostami.onlineservice.service.registration.RegistrationService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.rostami.onlineservice.util.ExceptionMessages.*;

@Getter
@Slf4j
public abstract class UserService<T extends User, ID extends Long, E extends BaseOutDto<T, E> > extends BaseService<T, ID, E> {

    private RegistrationService registrationService;
    private EmailTokenService emailTokenService;

    @Override
    public CreateUpdateResult save(BaseInDto<T> dto) {
        T entity = dto.convertToDomain();
        checkEmailExist(entity.getEmail());
        T saved = getRepository().save(entity);
        sendEmailVerificationToken(saved);
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    private void sendEmailVerificationToken(T saved) {
        String fulName = "Dear " + saved.getFirstname() + " " + saved.getLastname();
        Role role = saved.getRoles().stream().findFirst()
                .orElseThrow(() -> new EntityLoadException(NO_ROLE_MESSAGE));
        registrationService.sendToken(generateToken(saved), fulName, saved.getEmail(), role);
    }

    private void checkEmailExist(String email){
        long count = getRepository().count(((root, cq, cb) -> cb.equal(root.get("email"), email)));
        if (count > 0)
            throw new DuplicatedEmailException(EMAIL_EXIST_MESSAGE);
    }

    private String generateToken(User user){
        String token = UUID.randomUUID().toString();
        EmailToken confirmationToken = EmailToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        emailTokenService.save(confirmationToken);
        return token;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected CreateUpdateResult depositToCredit(T user, BigDecimal amount){
        Credit credit = user.getCredit();
        BigDecimal balance = credit.getBalance();
        credit.setBalance(balance.add(amount));
        user.setCredit(credit);
        T saved = getRepository().save(user);
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    @Override
    public CreateUpdateResult update(T entity) {
        checkEmailExistForUpdate(entity.getEmail());
        return super.update(entity);
    }

    private void checkEmailExistForUpdate(String email){
        long count = getRepository().count(((root, cq, cb) -> cb.equal(root.get("email"), email)));
        // one exist is for auto update when we try to set new value to fetched entity in convertToDomain Method
        if (count > 1)
            throw new DuplicatedEmailException(EMAIL_EXIST_MESSAGE);
    }

    public CreditFindResult loadCredit(ID id){
        T user = getRepository().findById(id).orElseThrow(() -> new EntityLoadException(ENTITY_ID_LOAD_MESSAGE));
        Credit credit = user.getCredit();
        return CreditFindResult.builder().build().convertToDto(credit);
    }

    @Autowired
    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Autowired
    public void setEmailTokenService(EmailTokenService emailTokenService) {
        this.emailTokenService = emailTokenService;
    }
}

