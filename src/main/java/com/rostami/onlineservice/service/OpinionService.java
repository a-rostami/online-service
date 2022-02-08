package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.dto.in.create.OpinionCreateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.AdFindResult;
import com.rostami.onlineservice.dto.out.single.OpinionFindResult;
import com.rostami.onlineservice.model.Opinion;
import com.rostami.onlineservice.model.enums.AdStatus;
import com.rostami.onlineservice.exception.NotAllowedToSubmitOpinionException;
import com.rostami.onlineservice.repository.OpinionRepository;
import com.rostami.onlineservice.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
public class OpinionService extends BaseService<Opinion, Long, OpinionFindResult> {
    private final OpinionRepository repository;
    private final AdService adService;
    private final ExpertService expertService;

    @Autowired
    public OpinionService(OpinionRepository repository, @Lazy AdService adService, @Lazy ExpertService expertService) {
        this.repository = repository;
        this.adService = adService;
        this.expertService = expertService;
    }

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(OpinionFindResult.builder().build());
    }

    @Override
    public CreateUpdateResult save(BaseInDto<Opinion> dto) {
        return submitOpinion((OpinionCreateParam) dto);
    }

    @Transactional
    public CreateUpdateResult submitOpinion(OpinionCreateParam opinionCreateParam){
        Opinion opinion = opinionCreateParam.convertToDomain();
        Long adId = opinion.getAd().getId();
        AdFindResult ad = (AdFindResult) adService.get(adId);
        checkPermission(ad);
        Opinion saved = repository.save(opinion);

        Long expertId = opinionCreateParam.getExpertId();
        expertService.updateAveragePoint(expertId, Double.valueOf(opinionCreateParam.getRate()));
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    private void checkPermission(AdFindResult ad){
        if (!ad.getAdStatus().equals(AdStatus.DONE) || !ad.getAdStatus().equals(AdStatus.PAID))
            throw new NotAllowedToSubmitOpinionException("You Can't submit opinion until it's done");
    }
}