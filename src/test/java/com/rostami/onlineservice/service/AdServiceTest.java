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

    @Autowired
    OfferService offerService;

    @Test
    void order_byPrice_isOk(){
        Ad ad = adService.findById(10L);
        List<Offer> adOffers = offerService.findAll(((root, query, cb) -> cb.equal(root.get("ad"), ad)));
        List<Offer> sortedOffers = adService.orderOffersByPrice(ad);
        adOffers.sort(Comparator.comparing(Offer::getPrice));
        boolean result = true;
        for (int i = 0; i < adOffers.size(); i++){
            if (!adOffers.get(i).equals(sortedOffers.get(i))) {
                result = false;
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    void order_byExpert_point_isOk(){
        // FIXME: 1/2/2022 add more expert for test average point sort
    }

    @Test
    void choose_expert_isOk(){
        // exist ad is new in DB
        Ad ad = adService.findById(10L);
        Expert expert = expertService.findById(4L);
        adService.chooseExpert(ad, expert);
        assertEquals(ad.getStatus(), AdStatus.WAITING_FOR_EXPERT);
    }
}