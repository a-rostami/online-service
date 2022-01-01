package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.enums.AdStatus;
import com.rostami.onlineservice.repository.AdRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AdService extends BaseService<Ad, Long> {
    private final AdRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }

    public void createAd(Customer customer, Ad ad){
        ad.setCustomer(customer);
        ad.setStatus(AdStatus.WAITING_FOR_OFFER);
        repository.save(ad);
    }
}