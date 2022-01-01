package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.MainServ;
import com.rostami.onlineservice.repository.MainServRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class MainServService extends BaseService<MainServ, Long> {
    private final MainServRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }
}
