package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.AdFindResult;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.dto.out.single.SubServFindResult;
import com.rostami.onlineservice.model.Ad;
import com.rostami.onlineservice.model.Customer;
import com.rostami.onlineservice.model.Expert;
import com.rostami.onlineservice.model.enums.AdStatus;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.AdRepository;
import com.rostami.onlineservice.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService extends BaseService<Ad, Long, AdFindResult> {
    private final AdRepository repository;
    private final ExpertService expertService;

    @Autowired
    public AdService(AdRepository repository, @Lazy ExpertService expertService) {
        this.repository = repository;
        this.expertService = expertService;
    }

    @PostConstruct
    public void init() {
        setRepository(repository);
        setBaseOutDto(AdFindResult.builder().build());
    }

    @Transactional
    public List<AdFindResult> findAllAdsOfCustomer(Long customerId, Pageable pageable){
        Customer customer = Customer.builder().id(customerId).build();
        return findAll((root, query, cb) -> cb.equal(root.get("customer"), customer), pageable)
                .stream().map(ad -> (AdFindResult) ad).collect(Collectors.toList());
    }

    @Transactional
    public List<AdFindResult> findAdsRelatedToExpertSubServ(Long expertId, Pageable pageable){
        ExpertFindResult expert = (ExpertFindResult) expertService.get(expertId);
        List<SubServFindResult> subServFindResults = expert.getSubServFindResults();

        return repository.findAll((root, query, cb) -> root.get("subServ").in(subServFindResults), pageable)
                .stream().map(ad -> new AdFindResult().convertToDto(ad)).collect(Collectors.toList());
    }

    @Transactional
    public CreateUpdateResult chooseExpert(Long adId, Long expertId){
        Ad ad = repository.findById(adId).orElseThrow(() -> new EntityLoadException("There is no Ad with this id!"));
        ad.setChosenExpert(Expert.builder().id(expertId).build());
        ad.setStatus(AdStatus.STARTED);
        return CreateUpdateResult.builder().id(adId).success(true).build();
    }
}