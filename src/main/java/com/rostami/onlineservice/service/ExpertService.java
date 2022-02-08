package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.dto.out.single.SubServFindResult;
import com.rostami.onlineservice.model.*;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.ExpertRepository;
import com.rostami.onlineservice.service.base.UserService;
import com.rostami.onlineservice.service.registration.EmailTokenService;
import com.rostami.onlineservice.service.registration.RegistrationService;
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
    private final RegistrationService registrationService;
    private final EmailTokenService emailTokenService;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(ExpertFindResult.builder().build());
        setRegistrationService(registrationService);
        setEmailTokenService(emailTokenService);
    }

    @Transactional
    public boolean enableCustomer(String email){
        int result = repository.enableCustomer(email);
        return result > 0;
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
        return expert.getAveragePoint();
    }

    @Transactional
    public void updateAveragePoint(Long id, double point){
        Expert expert = repository.findById(id).orElseThrow(() -> new EntityLoadException("There is no expert with this id"));

        // plus one for current point we will add
        long opinionCounts = opinionService.count((root, query, cb) -> cb.equal(root.get("expert"), expert)) + 1;

        double averagePoint = expert.getAveragePoint();
        double plusOfPreviousAveragePoints = opinionCounts * averagePoint;

        expert.setAveragePoint((plusOfPreviousAveragePoints + point) / opinionCounts);
        repository.save(expert);
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

    @Transactional
    public CreateUpdateResult unlockExpert(Long id){
        int countOfChangedRows = repository.unlockExpert(id);
        if (countOfChangedRows < 1)
            throw new EntityLoadException("There Is No Expert With This Id!");
        return CreateUpdateResult.builder().id(id).success(true).build();
    }
}
