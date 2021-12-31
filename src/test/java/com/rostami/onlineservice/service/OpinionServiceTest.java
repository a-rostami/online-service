package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.Opinion;
import com.rostami.onlineservice.exception.NotAllowedToSubmitOpinionException;
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

    @Test
    void not_allowed_submit_opinion_throws_isOk(){
        Expert expert = expertService.findById(1L);
        Ad ad = adService.findById(1L);
        Opinion opinion = Opinion.builder()
                .id(1L)
                .description("That Was Great...")
                .rate(4)
                .build();
        opinionService.save(opinion);
        assertThrows(NotAllowedToSubmitOpinionException.class, () ->
                opinionService.submitOpinion(opinion, expert, ad));
    }
}