package com.rostami.onlinehomeservices.service.base;

import com.rostami.onlinehomeservices.dto.in.BaseInDto;
import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.CreditFindResult;
import com.rostami.onlinehomeservices.model.Credit;
import com.rostami.onlinehomeservices.model.base.User;
import com.rostami.onlinehomeservices.exception.DuplicatedEmailException;
import com.rostami.onlinehomeservices.exception.EntityLoadException;
import com.rostami.onlinehomeservices.model.registration.EmailToken;
import com.rostami.onlinehomeservices.model.security.auth.Role;
import com.rostami.onlinehomeservices.service.registration.EmailTokenService;
import com.rostami.onlinehomeservices.service.registration.RegistrationService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.*;

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

