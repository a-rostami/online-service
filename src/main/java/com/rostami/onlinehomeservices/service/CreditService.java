package com.rostami.onlinehomeservices.service;

import com.rostami.onlinehomeservices.dto.out.single.CreditFindResult;
import com.rostami.onlinehomeservices.model.Credit;
import com.rostami.onlinehomeservices.repository.CreditRepository;
import com.rostami.onlinehomeservices.repository.impl.UpdateRepositoryImpl;
import com.rostami.onlinehomeservices.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CreditService extends BaseService<Credit, Long, CreditFindResult> {
    private final CreditRepository repository;
    private final UpdateRepositoryImpl<Credit, Long> updateRepository;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setUpdateRepositoryImpl(updateRepository);
        setBaseOutDto(CreditFindResult.builder().build());
    }
}
