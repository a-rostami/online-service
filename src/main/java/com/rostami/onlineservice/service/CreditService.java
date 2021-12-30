package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Credit;
import com.rostami.onlineservice.repository.CreditRepository;
import com.rostami.onlineservice.service.abstracts.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditService extends BaseService<Credit, Long> {
    private final CreditRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }
}
