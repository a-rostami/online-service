package com.rostami.onlinehomeservices.service;

import com.rostami.onlinehomeservices.dto.out.single.MainServFindResult;
import com.rostami.onlinehomeservices.model.MainServ;
import com.rostami.onlinehomeservices.repository.MainServRepository;
import com.rostami.onlinehomeservices.repository.impl.UpdateRepositoryImpl;
import com.rostami.onlinehomeservices.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class MainServService extends BaseService<MainServ, Long, MainServFindResult> {
    private final MainServRepository repository;
    private final UpdateRepositoryImpl<MainServ, Long> updateRepository;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setUpdateRepositoryImpl(updateRepository);
        setBaseOutDto(MainServFindResult.builder().build());
    }
}