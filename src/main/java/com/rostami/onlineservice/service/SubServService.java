package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.out.single.SubServFindResult;
import com.rostami.onlineservice.entity.SubServ;
import com.rostami.onlineservice.repository.SubServRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class SubServService extends BaseService<SubServ, Long> {
    private final SubServRepository repository;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(SubServFindResult.builder().build());
    }
}