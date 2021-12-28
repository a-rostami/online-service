package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Credit;
import com.rostami.onlineservice.repository.CreditRepository;
import com.rostami.onlineservice.service.abstracts.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CreditService extends BaseService<Credit, Long> {
    private final CreditRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }
}
