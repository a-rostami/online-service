package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.in.create.OfferCreateParam;
import com.rostami.onlineservice.dto.out.single.OfferFindResult;
import com.rostami.onlineservice.entity.Offer;
import com.rostami.onlineservice.exception.BelowBasePriceException;
import com.rostami.onlineservice.repository.OfferRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Builder
public class OfferService extends BaseService<Offer, Long> {
    private final OfferRepository repository;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(OfferFindResult.builder().build());
    }

    @Transactional
    public void submitOffer(OfferCreateParam offerCreateParam){
        Offer offer = offerCreateParam.convertToDomain();
        checkBasePrice(offer);
        repository.save(offer);
    }

    private void checkBasePrice(Offer offer){
        BigDecimal basePrice = offer.getAd().getSubServ().getBasePrice();
        if (offer.getPrice().compareTo(basePrice) < 0)
            throw new BelowBasePriceException("OfferPrice Is Below SubService Base price.");
    }
}
