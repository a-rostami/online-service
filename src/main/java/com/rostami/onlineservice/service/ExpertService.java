package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.dto.out.single.OpinionFindResult;
import com.rostami.onlineservice.dto.out.single.SubServFindResult;
import com.rostami.onlineservice.model.*;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.ExpertRepository;
import com.rostami.onlineservice.service.base.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertService extends UserService<Expert, Long, ExpertFindResult> {
    private final ExpertRepository repository;
    private final OpinionService opinionService;
    private final AdService adService;
    private final SubServService subServService;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(ExpertFindResult.builder().build());
    }

    @Transactional
    public CreateUpdateResult addSubServ(Long expertId, Long subServId){
        SubServFindResult subServFindResult = (SubServFindResult) subServService.get(subServId);
        SubServ subServ = SubServ.builder()
                .id(subServId)
                .basePrice(subServFindResult.getBasePrice())
                .name(subServFindResult.getName())
                .build();

        Expert expert = repository.findById(expertId).orElseThrow(
                () -> new EntityLoadException("There is no expert with this id"));

        List<SubServ> subServs = expert.getSubServs();
        subServs.add(subServ);
        expert.setSubServs(subServs);

        Expert saved = repository.save(expert);
        return CreateUpdateResult.builder().success(true).id(saved.getId()).build();
    }

    @Transactional
    public Double getAveragePoint(Long id){
        Expert expert = repository.findById(id).orElseThrow(() -> new EntityLoadException("There is no expert with this id"));
        List<OpinionFindResult> opinions =
                opinionService.findAll(((root, query, cb) -> cb.equal(root.get("expert"), expert)))
                        .stream().map(opinionDto -> (OpinionFindResult) opinionDto).toList();
        return opinions.stream().mapToInt(OpinionFindResult::getRate).average().orElse(0);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult depositToCredit(Long expertId, BigDecimal amount){
        Expert expert = repository.findById(expertId).orElseThrow(() ->
                new EntityLoadException("There Is No Expert With This ID!"));
        return super.depositToCredit(expert, amount);
    }

    @Transactional
    public long getNumberOfDoneAds(Long expertId){
        Expert expert = Expert.builder().id(expertId).build();
        return adService.count((root, query, cb) -> cb.equal(root.get("chosenExpert"), expert));
    }
}
