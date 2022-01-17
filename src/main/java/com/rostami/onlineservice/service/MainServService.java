package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.dto.out.single.CreditFindResult;
import com.rostami.onlineservice.dto.out.single.MainServFindResult;
import com.rostami.onlineservice.entity.Credit;
import com.rostami.onlineservice.entity.MainServ;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.MainServRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServService extends BaseService<MainServ, Long> {
    private final MainServRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
        setBaseOutDto(MainServFindResult.builder().build());
    }

    @Transactional(readOnly = true)
    public List<MainServ> findAll(Specification<MainServ> specification){
        return repository.findAll(specification);
    }

    @Transactional(readOnly = true)
    public List<MainServ> findAll(Specification<MainServ> specification, Sort sort){
        return repository.findAll(specification, sort);
    }

    @Transactional(readOnly = true)
    public List<MainServ> findAll(Specification<MainServ> specification, Pageable pageable){
        return repository.findAll(specification, pageable).getContent();
    }
}
