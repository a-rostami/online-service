package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Opinion;
import com.rostami.onlineservice.repository.OpinionRepository;
import com.rostami.onlineservice.service.abstracts.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class OpinionService extends BaseService<Opinion, Long> {
    private final OpinionRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }
}
