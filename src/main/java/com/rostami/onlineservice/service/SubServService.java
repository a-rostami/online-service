package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.SubServ;
import com.rostami.onlineservice.repository.SubServRepository;
import com.rostami.onlineservice.service.abstracts.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class SubServService extends BaseService<SubServ, Long> {
    private final SubServRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }
}
