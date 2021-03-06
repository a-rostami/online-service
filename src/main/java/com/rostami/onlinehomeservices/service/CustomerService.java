package com.rostami.onlinehomeservices.service;

import com.rostami.onlinehomeservices.dto.in.update.api.PasswordUpdateParam;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.CustomerFindResult;
import com.rostami.onlinehomeservices.exception.WrongPreviousPasswordException;
import com.rostami.onlinehomeservices.model.Credit;
import com.rostami.onlinehomeservices.model.Customer;
import com.rostami.onlinehomeservices.exception.EntityLoadException;
import com.rostami.onlinehomeservices.exception.NotEnoughCreditBalanceException;
import com.rostami.onlinehomeservices.repository.CustomerRepository;
import com.rostami.onlinehomeservices.repository.impl.UpdateRepositoryImpl;
import com.rostami.onlinehomeservices.service.base.UserService;
import com.rostami.onlinehomeservices.service.registration.EmailTokenService;
import com.rostami.onlinehomeservices.service.registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService extends UserService<Customer, Long, CustomerFindResult> {
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private final CustomerRepository repository;
    private final ExpertService expertService;
    private final AdService adService;
    private final RegistrationService registrationService;
    private final EmailTokenService emailTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UpdateRepositoryImpl<Customer, Long> updateRepository;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setUpdateRepositoryImpl(updateRepository);
        setBaseOutDto(CustomerFindResult.builder().build());
        setRegistrationService(registrationService);
        setEmailTokenService(emailTokenService);
    }


    @Transactional
    public boolean enableCustomer(String email){
        int result = repository.enableCustomer(email);
        return result > 0;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult depositToCredit(Long customerId, BigDecimal amount){
        Customer customer = repository.findById(customerId).orElseThrow(() ->
                new EntityLoadException(ENTITY_ID_LOAD_MESSAGE));
        return super.depositToCredit(customer, amount);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult purchase(Long customerId, Long expertId, BigDecimal amount){
        Customer customer = repository.findById(customerId).orElseThrow(() ->
                new EntityLoadException(ENTITY_ID_LOAD_MESSAGE));
        Credit credit = customer.getCredit();
        BigDecimal balance = credit.getBalance();

        if (balance.compareTo(amount) <= 0)
            throw new NotEnoughCreditBalanceException(NOT_ENOUGH_BALANCE_MESSAGE);

        credit.setBalance(balance.subtract(amount));
        customer.setCredit(credit);

        depositToExpertAndAdmin(expertId, amount);
        Customer saved = repository.save(customer);
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    private void depositToExpertAndAdmin(Long expertId, BigDecimal amount) {
        BigDecimal percentage = amount.multiply(BigDecimal.valueOf(70)).divide(ONE_HUNDRED, 2, RoundingMode.UP);
        expertService.depositToCredit(expertId, percentage);
    }

    @Transactional
    public long getNumberOfCustomerAds(Long customerId){
        Customer customer = Customer.builder().id(customerId).build();
        return adService.count((root, query, cb) -> cb.equal(root.get("customer"), customer));
    }

    @Transactional
    public CreateUpdateResult changePassword(PasswordUpdateParam param){
        Customer customer = repository.findByEmail(param.getEmail())
                .orElseThrow(() -> new EntityLoadException(ENTITY_EMAIL_LOAD_MESSAGE));

        String customerPassword = customer.getPassword();

        if (!bCryptPasswordEncoder.matches(param.getPreviousPassword(), customerPassword))
            throw new WrongPreviousPasswordException(WRONG_PREVIOUS_PASSWORD_MESSAGE);

        customer.setPassword(param.getNewPassword());
        Customer saved = repository.save(customer);

        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }
}
