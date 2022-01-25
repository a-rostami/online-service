package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.in.update.ExpertUpdateParam;
import com.rostami.onlineservice.dto.out.single.AdFindResult;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.Offer;
import com.rostami.onlineservice.entity.enums.AdStatus;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.AdRepository;
import com.rostami.onlineservice.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService extends BaseService<Ad, Long> {
    private final AdRepository repository;

    private final ExpertService expertService;
    private final OfferService offerService;

    @Autowired
    public AdService(AdRepository repository, @Lazy ExpertService expertService, @Lazy OfferService offerService) {
        this.repository = repository;
        this.expertService = expertService;
        this.offerService = offerService;
    }

    @PostConstruct
    public void init() {
        setRepository(repository);
        setBaseOutDto(AdFindResult.builder().build());
    }

    @Transactional
    public List<AdFindResult> findAllAdsOfCustomer(Long customerId){
        Customer customer = Customer.builder().id(customerId).build();
        List<Ad> ads = findAll((root, query, cb) -> cb.equal(root.get("customer"), customer));
        return ads.stream().map(p -> AdFindResult.builder().build().convertToDto(p))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Offer> orderOffersByPrice(Long adId) {
        Ad ad = repository.findById(adId).orElseThrow(() -> new EntityLoadException("There is no Ad with this id!"));
        Sort byPrice = Sort.by(Sort.Direction.ASC, "price");
        return offerService.findAll(((root, cq, cb) -> cb.equal(root.get("ad"), ad)), byPrice);
    }

    @Transactional(readOnly = true)
    public List<Offer> orderOffersByExpertPoint(Long adId) {
        Ad ad = repository.findById(adId).orElseThrow(() -> new EntityLoadException("There is no Ad with this id!"));
        List<Offer> offers = offerService.findAll(((root, query, cb) -> cb.equal(root.get("ad"), ad)));
        offers.sort(Comparator.comparing(offer -> expertService.getAveragePoint(offer.getExpert().getId())));
        return offers;
    }

    @Transactional
    public void chooseExpert(Long adId, ExpertUpdateParam expertParam){
        Ad ad = repository.findById(adId).orElseThrow(() -> new EntityLoadException("There is no Ad with this id!"));
        ad.setChosenExpert(expertParam.convertToDomain());
        ad.setStatus(AdStatus.WAITING_FOR_EXPERT);
        repository.save(ad);
    }
}