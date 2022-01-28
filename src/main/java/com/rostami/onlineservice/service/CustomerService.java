package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.CustomerFindResult;
import com.rostami.onlineservice.entity.Credit;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.exception.NotEnoughCreditBalanceException;
import com.rostami.onlineservice.repository.CustomerRepository;
import com.rostami.onlineservice.service.base.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CustomerService extends UserService<Customer, Long> {
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private final CustomerRepository repository;
    private final ExpertService expertService;
    private final AdService adService;


    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(CustomerFindResult.builder().build());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult depositToCredit(Long customerId, BigDecimal amount){
        Customer customer = repository.findById(customerId).orElseThrow(() ->
                new EntityLoadException("There Is No Customer With This ID!"));
        return super.depositToCredit(customer, amount);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult purchase(Long customerId, Long expertId, BigDecimal amount){
        Customer customer = repository.findById(customerId).orElseThrow(() ->
                new EntityLoadException("There Is No Customer With This Id!"));
        Credit credit = customer.getCredit();
        BigDecimal balance = credit.getBalance();

        if (balance.compareTo(amount) <= 0)
            throw new NotEnoughCreditBalanceException("Not Enough Balance Please Charge Your Account!");
        credit.setBalance(balance.subtract(amount));
        customer.setCredit(credit);

        BigDecimal percentage = amount.multiply(BigDecimal.valueOf(70)).divide(ONE_HUNDRED, 2, RoundingMode.UP);
        expertService.depositToCredit(expertId, percentage);
        Customer saved = repository.save(customer);
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    @Transactional
    public long getNumberOfRelatedAds(Long customerId){
        Customer customer = Customer.builder().id(customerId).build();
        return adService.count((root, query, cb) -> cb.equal(root.get("customer"), customer));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changePassword(Long id, String newPassword){
        repository.changePassword(id, newPassword);
    }
}
