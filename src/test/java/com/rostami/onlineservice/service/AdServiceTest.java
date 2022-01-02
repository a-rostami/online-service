package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.entity.*;
import com.rostami.onlineservice.entity.enums.AdStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@SpringJUnitConfig({AppConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdServiceTest {
    @Autowired
    AdService adService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ExpertService expertService;

    @Autowired
    SubServService subServService;

    @Test
    void add_newAd_isOk() {
        Customer customer = customerService.findById(1L);
        SubServ subServ = subServService.findById(1L);
        Ad ad = Ad.builder()
                .id(1L)
                .address("Tabriz")
                .completionDate(Date.valueOf("2021-12-30"))
                .completionTime(Time.valueOf("21:00:00"))
                .price(BigDecimal.valueOf(100000L))
                .recordTime(Time.valueOf("24:00:00"))
                .recordDate(Date.valueOf("2022-01-31"))
                .workDescription("I Want You To Do SomeThing Good")
                .status(AdStatus.WAITING_FOR_OFFER)
                .customer(customer)
                .subServ(subServ)
                .build();
        customer.setAds(List.of(ad));
        adService.save(ad);
        customerService.save(customer);
        Ad entity = customerService.findById(1L).getAds().get(0);
        assertEquals(ad, entity);
    }

    @Test
    void order_byPrice_isOk(){
        Ad ad = adService.findById(1L);
        List<Offer> adOffers = ad.getOffers();
        List<Offer> sortedOffers = adService.orderOffersByPrice(ad);
        adOffers.sort(Comparator.comparing(Offer::getPrice));
        assertEquals(adOffers, sortedOffers);
    }

    @Test
    void order_byExpert_point_isOk(){
        Ad ad = adService.findById(1L);
        List<Offer> offers = adService.orderOffersByExpertPoint(ad);
        List<Offer> adOffers = ad.getOffers();
        // at this point we only have 1 expert
        // FIXME: 1/2/2022 add more expert for test average point sort
        assertEquals(offers, adOffers);
    }

    @Test
    void choose_expert_isOk(){
        Ad ad = adService.findById(1L);
        Expert expert = expertService.findById(1L);
        adService.chooseExpert(ad, expert);
        assertEquals(ad.getStatus(), AdStatus.WAITING_FOR_EXPERT);
    }

}