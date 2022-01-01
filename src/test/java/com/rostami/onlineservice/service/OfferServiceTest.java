package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.Offer;
import com.rostami.onlineservice.exception.BelowBasePriceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@SpringJUnitConfig({AppConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OfferServiceTest {
    @Autowired
    OfferService offerService;

    @Autowired
    AdService adService;

    @Autowired
    ExpertService expertService;

    @Test
    void submit_offer_isOk(){
        Expert expert = expertService.findById(1L);
        Ad ad = adService.findById(1L);
        Offer offer = Offer.builder()
                .id(1L)
                .startDate(Date.valueOf("2021-10-31"))
                .startTime(Time.valueOf("12:30:10"))
                .completionDate(Date.valueOf("2023-10-31"))
                .completionTime(Time.valueOf("18:30:10"))
                .recordTime(Time.valueOf("24:30:10"))
                .recordDate(Date.valueOf("2020-10-31"))
                .price(BigDecimal.valueOf(450000L))
                .expert(expert)
                .ad(ad)
                .build();
        offerService.submitOffer(offer);
        assertEquals(offer, adService.findById(1L).getOffers().get(0));
    }

    @Test
    void submit_offer_throws_below_priceException_isOk(){
        Offer offer = offerService.findById(1L);
        // base price of subService of this ad is 120000
        offer.setPrice(BigDecimal.valueOf(100000));
        assertThrows(BelowBasePriceException.class, () -> offerService.submitOffer(offer));
    }
}