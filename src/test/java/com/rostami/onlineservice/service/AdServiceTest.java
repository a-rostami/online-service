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

    @Test
    void order_byPrice_isOk(){
        Ad ad = adService.findById(10L);
        List<Offer> adOffers = ad.getOffers();
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
        Ad ad = adService.findById(10L);
        List<Offer> offers = adService.orderOffersByExpertPoint(ad);
        List<Offer> adOffers = ad.getOffers();
        // at this point we only have 1 expert
        // FIXME: 1/2/2022 add more expert for test average point sort
    }

    @Test
    void choose_expert_isOk(){
        Ad ad = adService.findById(10L);
        Expert expert = expertService.findById(4L);
        adService.chooseExpert(ad, expert);
        assertEquals(ad.getStatus(), AdStatus.WAITING_FOR_EXPERT);
    }

}