package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.SubServ;
import com.rostami.onlineservice.repository.SubServRepository;
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
public class SubServService extends BaseService<SubServ, Long> {
    private final SubServRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }

    @Transactional(readOnly = true)
    public List<SubServ> findAll(Specification<SubServ> specification){
        return repository.findAll(specification);
    }

    @Transactional(readOnly = true)
    public List<SubServ> findAll(Specification<SubServ> specification, Sort sort){
        return repository.findAll(specification, sort);
    }

    @Transactional(readOnly = true)
    public List<SubServ> findAll(Specification<SubServ> specification, Pageable pageable){
        return repository.findAll(specification, pageable).getContent();
    }
}
