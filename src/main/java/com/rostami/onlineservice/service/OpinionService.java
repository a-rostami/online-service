package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.Opinion;
import com.rostami.onlineservice.entity.enums.AdStatus;
import com.rostami.onlineservice.exception.NotAllowedToSubmitOpinionException;
import com.rostami.onlineservice.repository.OpinionRepository;
import com.rostami.onlineservice.service.abstracts.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class OpinionService extends BaseService<Opinion, Long> {
    private final OpinionRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }

    @Transactional
    public void submitOpinion(Opinion opinion, Expert expert, Ad ad){
        checkPermission(ad);
        opinion.setExpert(expert);
        opinion.setAd(ad);
        repository.save(opinion);
    }

    private void checkPermission(Ad ad){
        if (!ad.getStatus().equals(AdStatus.DONE) || !ad.getStatus().equals(AdStatus.PAID))
            throw new NotAllowedToSubmitOpinionException("You Can't submit opinion until it's done");
    }
}
