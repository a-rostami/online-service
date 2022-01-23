package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.Opinion;
import com.rostami.onlineservice.exception.NotAllowedToSubmitOpinionException;
import com.rostami.onlineservice.repository.AdRepository;
import com.rostami.onlineservice.repository.ExpertRepository;
import com.rostami.onlineservice.repository.OpinionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@SpringJUnitConfig({AppConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OpinionServiceTest {
    @Autowired
    OpinionService opinionService;

    @Autowired
    ExpertService expertService;

    @Autowired
    AdService adService;

    @Autowired
    AdRepository adRepository;

    @Autowired
    ExpertRepository expertRepository;

    @Autowired
    OpinionRepository opinionRepository;

    @Test
    void not_allowed_submit_opinion_throws_isOk(){
        // relations between expert , ad, opinion are created.
        Expert expert = expertRepository.findById(4L).orElse(new Expert());
        // ad status of this ad is : WAITING FOR OFFER
        Ad ad = adRepository.findById(10L).orElse(new Ad());
        Opinion opinion = opinionRepository.findById(15L).orElse(new Opinion());
//        assertThrows(NotAllowedToSubmitOpinionException.class, () ->
//                opinionService.submitOpinion(opinion, expert, ad));
    }}