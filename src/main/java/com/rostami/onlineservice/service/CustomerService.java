package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.exception.DuplicateEmailException;
import com.rostami.onlineservice.repository.CustomerRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CustomerService extends BaseService<Customer, Long> {
    private final CustomerRepository repository;
    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }

    @Override
    public void save(Customer entity) {
        checkEmailExist(entity.getEmail(), entity.getId());
        repository.save(entity);
    }

    private void checkEmailExist(String email, Long id){
        Customer byEmail = repository.findByEmail(email);
        if (byEmail != null  && !byEmail.getId().equals(id))
            throw new DuplicateEmailException("Email Exist!");
    }

    @Transactional
    public Customer findByEmail(String email){
        return repository.findByEmail(email);
    }

    @Transactional
    public Customer findByUsername(String username){
        return repository.findByUsername(username);
    }

    @Transactional
    public Customer findByUserNameAndPassword(String username, String password){
        return repository.findByUsernameAndPassword(username, password);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changePassword(Long id, String newPassword){
        repository.changePassword(id, newPassword);
    }
}
