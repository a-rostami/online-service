package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.model.Expert;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@DataJpaTest
@ActiveProfiles("test")
@SpringJUnitConfig({AppConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExpertServiceTest {
    @Autowired
    ExpertService expertService;

    @Autowired
    OpinionService opinionService;

    @Autowired
    AdService adService;

    @Test
    void get_average_point_isOk() {
        ExpertFindResult expertDto = (ExpertFindResult) expertService.get(4L);
        Expert expert = Expert.builder().id(expertDto.getId()).build();
//        Double averagePoint = expertService.getAveragePoint(expert);
//        assertEquals(averagePoint, 3.5);
    }
//
//    @Test
//    void test_find_all_relatedAds_isOk() {
//        List<AdFindResult> adsRelatedToSubServ = expertService.findAdsRelatedToExpertSubServ(4L);
//        // id 9 exist in DB
//        SubServ subServ = SubServ.builder().id(9L).build();
//        List<AdFindResult> ads = adService.findAll(((root, query, cb) -> cb.equal(root.get("subServ"), subServ)))
//                .stream().map(p -> AdFindResult.builder().build()
//                        .convertToDto(p)).collect(Collectors.toList());
//        boolean result = true;
//        for (int i = 0; i < adsRelatedToSubServ.size(); i++){
//            if (!adsRelatedToSubServ.get(i).getId().equals(ads.get(i).getId())) {
//                result = false;
//                break;
//            }
//        }
//        assertTrue(result);
//    }
}