package com.rostami.onlinehomeservices.service;

import com.rostami.onlinehomeservices.dto.out.single.SubServFindResult;
import com.rostami.onlinehomeservices.model.MainServ;
import com.rostami.onlinehomeservices.model.SubServ;
import com.rostami.onlinehomeservices.repository.SubServRepository;
import com.rostami.onlinehomeservices.repository.impl.UpdateRepositoryImpl;
import com.rostami.onlinehomeservices.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubServService extends BaseService<SubServ, Long, SubServFindResult> {
    private final SubServRepository repository;
    private final UpdateRepositoryImpl<SubServ, Long> updateRepository;


    @PostConstruct
    public void init(){
        setRepository(repository);
        setUpdateRepositoryImpl(updateRepository);
        setBaseOutDto(SubServFindResult.builder().build());
    }

    @Transactional
    public Set<SubServFindResult> findSubServsOfMainServ(Long mainServId){
        MainServ mainServ = MainServ.builder().id(mainServId).build();
        List<SubServ> result = repository.findAll((root, query, cb) -> cb.equal(root.get("mainServ"), mainServ));
        return result.stream().map(subServ -> SubServFindResult.builder().build().convertToDto(subServ)).collect(Collectors.toSet());
    }
}