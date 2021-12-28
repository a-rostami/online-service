package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.repository.CustomerRepository;
import com.rostami.onlineservice.service.abstracts.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CustomerService extends BaseService<Customer, Long> {
    private final CustomerRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }
}
