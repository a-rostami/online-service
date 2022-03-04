package com.rostami.onlinehomeservices.service;

import com.rostami.onlinehomeservices.dto.in.BaseInDto;
import com.rostami.onlinehomeservices.dto.in.create.OfferCreateParam;
import com.rostami.onlinehomeservices.dto.in.update.OfferUpdateParam;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.AdFindResult;
import com.rostami.onlinehomeservices.dto.out.single.OfferFindResult;
import com.rostami.onlinehomeservices.exception.EntityLoadException;
import com.rostami.onlinehomeservices.model.Ad;
import com.rostami.onlinehomeservices.model.Expert;
import com.rostami.onlinehomeservices.model.Offer;
import com.rostami.onlinehomeservices.exception.BelowBasePriceException;
import com.rostami.onlinehomeservices.repository.OfferRepository;
import com.rostami.onlinehomeservices.repository.impl.UpdateRepositoryImpl;
import com.rostami.onlinehomeservices.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.ENTITY_ID_LOAD_MESSAGE;
import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.OFFER_LOW_BASE_PRICE_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfferService extends BaseService<Offer, Long, OfferFindResult> {
    private final OfferRepository repository;
    private final AdService adService;
    private final UpdateRepositoryImpl<Offer, Long> updateRepository;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setUpdateRepositoryImpl(updateRepository);
        setBaseOutDto(OfferFindResult.builder().build());
    }

    @Override
    public CreateUpdateResult save(BaseInDto<Offer> dto) {
        return submitOffer((OfferCreateParam) dto);
    }


    public CreateUpdateResult update(OfferUpdateParam param, Long id) {
        Offer offer = repository.findById(id).orElseThrow(() -> new EntityLoadException(ENTITY_ID_LOAD_MESSAGE));
        checkBasePrice(offer);
        return super.update(param, id);
    }

    @Transactional
    public CreateUpdateResult submitOffer(OfferCreateParam offerCreateParam){
        Offer offer = offerCreateParam.convertToDomain();
        checkBasePrice(offer);
        Offer saved = repository.save(offer);
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    private void checkBasePrice(Offer offer){
        AdFindResult ad = (AdFindResult) adService.findById(offer.getAd().getId());
        if (offer.getPrice().compareTo(ad.getSubServFindResult().getBasePrice()) > 0)
            throw new BelowBasePriceException(OFFER_LOW_BASE_PRICE_MESSAGE);
    }

    @Transactional
    public Set<OfferFindResult> loadAllOffersOfExpert(Long expertId, Pageable pageable){
        Expert expert = Expert.builder().id(expertId).build();
        List<Offer> offers = repository.findAll((root, query, cb) -> cb.equal(root.get("expert"), expert), pageable).getContent();
        return offers.stream().map(offer -> OfferFindResult.builder().build().convertToDto(offer)).collect(Collectors.toSet());
    }


    @Transactional(readOnly = true)
    public List<OfferFindResult> orderOffersByPrice(Long adId, Pageable pageable) {
        Ad ad = Ad.builder().id(adId).build();
        return repository.findAll((root, query, cb) -> cb.equal(root.get("ad"), ad), pageable).getContent()
                .stream().map(offer -> new OfferFindResult().convertToDto(offer)).toList();
    }

}
