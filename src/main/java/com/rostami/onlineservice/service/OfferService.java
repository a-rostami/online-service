package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Offer;
import com.rostami.onlineservice.exception.BelowBasePriceException;
import com.rostami.onlineservice.repository.OfferRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OfferService extends BaseService<Offer, Long> {
    private final OfferRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }

    @Transactional
    public void submitOffer(Offer offer){
        checkBasePrice(offer);
        repository.save(offer);
    }

    private void checkBasePrice(Offer offer){
        BigDecimal basePrice = offer.getAd().getSubServ().getBasePrice();
        if (offer.getPrice().compareTo(basePrice) < 0)
            throw new BelowBasePriceException("OfferPrice Is Below SubService Base price.");
    }

}
