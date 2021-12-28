package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.repository.AdRepository;
import com.rostami.onlineservice.service.abstracts.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AdService extends BaseService<Ad, Long> {
    private final AdRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }
}
