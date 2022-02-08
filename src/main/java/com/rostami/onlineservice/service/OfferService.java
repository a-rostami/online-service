package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.dto.in.create.OfferCreateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.OfferFindResult;
import com.rostami.onlineservice.model.Ad;
import com.rostami.onlineservice.model.Expert;
import com.rostami.onlineservice.model.Offer;
import com.rostami.onlineservice.exception.BelowBasePriceException;
import com.rostami.onlineservice.repository.OfferRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService extends BaseService<Offer, Long, OfferFindResult> {
    private final OfferRepository repository;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(OfferFindResult.builder().build());
    }

    @Override
    public CreateUpdateResult save(BaseInDto<Offer> dto) {
        return submitOffer((OfferCreateParam) dto);
    }

    @Transactional
    public CreateUpdateResult submitOffer(OfferCreateParam offerCreateParam){
        Offer offer = offerCreateParam.convertToDomain();
        checkBasePrice(offer);
        Offer saved = repository.save(offer);
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    private void checkBasePrice(Offer offer){
        BigDecimal basePrice = offer.getAd().getSubServ().getBasePrice();
        if (offer.getPrice().compareTo(basePrice) < 0)
            throw new BelowBasePriceException("OfferPrice Is Below SubService Base price.");
    }

    @Transactional
    public List<OfferFindResult> loadAllOffersOfExpert(Long expertId, Pageable pageable){
        Expert expert = Expert.builder().id(expertId).build();
        List<Offer> offers = repository.findAll((root, query, cb) -> cb.equal(root.get("expert"), expert), pageable).getContent();
        return offers.stream().map(offer -> OfferFindResult.builder().build().convertToDto(offer)).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<OfferFindResult> orderOffersByPrice(Long adId, Pageable pageable) {
        Ad ad = Ad.builder().id(adId).build();
        return repository.findAll((root, query, cb) -> cb.equal(root.get("ad"), ad), pageable).getContent()
                .stream().map(offer -> new OfferFindResult().convertToDto(offer)).toList();
    }

}
