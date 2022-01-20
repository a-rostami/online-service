package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.dto.out.single.AdFindResult;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.Offer;
import com.rostami.onlineservice.entity.enums.AdStatus;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.AdRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdService extends BaseService<Ad, Long> {
    private final AdRepository repository;
    private final ExpertService expertService;
    private final OfferService offerService;

    @PostConstruct
    public void init() {
        setJpaRepository(repository);
        setBaseOutDto(AdFindResult.builder().build());
    }

    @Transactional(readOnly = true)
    public List<Offer> orderOffersByPrice(Ad ad) {
        Sort byPrice = Sort.by(Sort.Direction.ASC, "price");
        return offerService.findAll(((root, cq, cb) -> cb.equal(root.get("ad"), ad)), byPrice);
    }

    @Transactional(readOnly = true)
    public List<Offer> orderOffersByExpertPoint(Ad ad) {
        List<Offer> offers = offerService.findAll(((root, query, cb) -> cb.equal(root.get("ad"), ad)));
        offers.sort(Comparator.comparing(offer -> expertService.getAveragePoint(offer.getExpert())));
        return offers;
    }

    @Transactional
    public void chooseExpert(Ad ad, Expert expert){
        ad.setChosenExpert(expert);
        ad.setStatus(AdStatus.WAITING_FOR_EXPERT);
        repository.save(ad);
    }

    @Transactional(readOnly = true)
    public List<Ad> findAll(Specification<Ad> specification){
        return repository.findAll(specification);
    }

    @Transactional(readOnly = true)
    public List<Ad> findAll(Specification<Ad> specification, Sort sort){
        return repository.findAll(specification, sort);
    }

    @Transactional(readOnly = true)
    public List<Ad> findAll(Specification<Ad> specification, Pageable pageable){
        return repository.findAll(specification, pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<Ad> findAll(Sort sort){
        return repository.findAll(sort);
    }
}