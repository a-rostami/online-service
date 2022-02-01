package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.out.single.AdFindResult;
import com.rostami.onlineservice.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ActiveProfiles("test")
class AdServiceTest {
    @MockBean
    AdService adService;
    @MockBean
    OfferService offerService;

/*    @BeforeEach
    void init(){
        adService = Mockito.mock(AdService.class);
        offerService = Mockito.mock(OfferService.class);
        AdFindResult result = AdFindResult.builder().id(1L).build();
        Ad ad = Ad.builder().id(result.getId()).build();
        doReturn(result).when(adService).get(1L);

        Offer offer = Offer.builder().id(1L).price(BigDecimal.valueOf(150000)).build();
        Offer offer2 = Offer.builder().id(2L).price(BigDecimal.valueOf(140000)).build();
        Offer offer3 = Offer.builder().id(3L).price(BigDecimal.valueOf(130000)).build();
        doReturn(List.of(offer, offer2, offer3)).when(offerService).findAll(((root, query, cb) ->
                cb.equal(root.get("ad"), ad)));
        doReturn(List.of(offer3, offer2, offer)).when(adService).orderOffersByPrice(ad.getId());
    }


    @Test
    void order_byPrice_isOk() {
        AdFindResult dto = (AdFindResult) adService.get(1L);
        Ad ad = Ad.builder().id(dto.getId()).build();
        List<Offer> adOffers = offerService.findAll(((root, query, cb) -> cb.equal(root.get("ad"), ad)));
        List<Offer> sortedOffers = adService.orderOffersByPrice(ad.getId());
        adOffers.sort(Comparator.comparing(Offer::getPrice));
        boolean result = true;
        for (int i = 0; i < adOffers.size(); i++) {
            if (!adOffers.get(i).equals(sortedOffers.get(i))) {
                result = false;
                break;
            }
        }
        assertTrue(result);
    }*/
}