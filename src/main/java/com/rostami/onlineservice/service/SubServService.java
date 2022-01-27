package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.out.single.SubServFindResult;
import com.rostami.onlineservice.entity.MainServ;
import com.rostami.onlineservice.entity.SubServ;
import com.rostami.onlineservice.repository.SubServRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubServService extends BaseService<SubServ, Long> {
    private final SubServRepository repository;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(SubServFindResult.builder().build());
    }

    @Transactional
    public List<SubServFindResult> findSubServsOfMainServ(Long mainServId){
        MainServ mainServ = MainServ.builder().id(mainServId).build();
        List<SubServ> result = repository.findAll((root, query, cb) -> cb.equal(root.get("mainServ"), mainServ));
        return result.stream().map(subServ -> SubServFindResult.builder().build().convertToDto(subServ)).collect(Collectors.toList());
    }
}