package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.enums.Role;
import com.rostami.onlineservice.exception.DuplicateEmailException;
import com.rostami.onlineservice.repository.CustomerRepository;
import com.rostami.onlineservice.service.abstracts.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService extends BaseService<Customer, Long> {
    private final CustomerRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }

    @Override
    public void save(Customer entity) {
        checkEmailExist(entity.getEmail());
        repository.save(entity);
    }

    private void checkEmailExist(String email){
        Customer byEmail = repository.findByEmail(email);
        if (byEmail != null)
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
