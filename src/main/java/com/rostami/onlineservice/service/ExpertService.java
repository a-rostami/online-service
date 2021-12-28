package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.repository.ExpertRepository;
import com.rostami.onlineservice.service.abstracts.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ExpertService extends BaseService<Expert, Long> {
    private final ExpertRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }
}
