package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.dto.out.single.OfferFindResult;
import com.rostami.onlineservice.entity.Offer;
import com.rostami.onlineservice.exception.BelowBasePriceException;
import com.rostami.onlineservice.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;

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

    @Autowired
    OfferRepository offerRepository;

    @Test
    void submit_offer_throws_below_priceException_isOk(){
        OfferFindResult offerDto = (OfferFindResult) offerService.get(13L);
        Offer offer = offerRepository.findById(offerDto.getId()).orElse(new Offer());
        // base price of subService of this ad is 100000
        offer.setPrice(BigDecimal.valueOf(50000));
        assertThrows(BelowBasePriceException.class, () -> offerService.submitOffer(offer));
    }
}