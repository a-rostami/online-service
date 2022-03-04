package com.rostami.onlinehomeservices.service;

import com.rostami.onlinehomeservices.dto.in.BaseInDto;
import com.rostami.onlinehomeservices.dto.in.create.OpinionCreateParam;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.AdFindResult;
import com.rostami.onlinehomeservices.dto.out.single.OpinionFindResult;
import com.rostami.onlinehomeservices.model.Opinion;
import com.rostami.onlinehomeservices.exception.NotAllowedToSubmitOpinionException;
import com.rostami.onlinehomeservices.repository.OpinionRepository;
import com.rostami.onlinehomeservices.repository.impl.UpdateRepositoryImpl;
import com.rostami.onlinehomeservices.service.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static com.rostami.onlinehomeservices.model.enums.AdStatus.DONE;
import static com.rostami.onlinehomeservices.model.enums.AdStatus.PAID;
import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.NOT_ALLOWED_SUBMIT_OPINION_MESSAGE;

@Service
@Slf4j
public class OpinionService extends BaseService<Opinion, Long, OpinionFindResult> {
    private final OpinionRepository repository;
    private final AdService adService;
    private final ExpertService expertService;
    private final UpdateRepositoryImpl<Opinion, Long> updateRepository;

    @Autowired
    public OpinionService(OpinionRepository repository,
                          @Lazy AdService adService,
                          @Lazy ExpertService expertService,
                          UpdateRepositoryImpl<Opinion, Long> updateRepository) {
        this.repository = repository;
        this.adService = adService;
        this.expertService = expertService;
        this.updateRepository = updateRepository;
    }

    @PostConstruct
    public void init(){
        setRepository(repository);
        setUpdateRepositoryImpl(updateRepository);
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

        // check Ad Is Done or not
        checkPermission(ad);
        Opinion saved = repository.save(opinion);

        // updating expert average point
        Long expertId = opinionCreateParam.getExpertId();
        expertService.addNewPointToExpertAveragePoint(expertId, Double.valueOf(opinionCreateParam.getRate()));

        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    private void checkPermission(AdFindResult ad){
        if (!ad.getAdStatus().equals(DONE) && !ad.getAdStatus().equals(PAID))
            throw new NotAllowedToSubmitOpinionException(NOT_ALLOWED_SUBMIT_OPINION_MESSAGE);
    }
}