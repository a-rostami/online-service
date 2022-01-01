package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.Opinion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@SpringJUnitConfig({AppConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExpertServiceTest {
    @Autowired
    ExpertService expertService;

    @Autowired
    OpinionService opinionService;

    @Test
    void get_average_point_isOk(){
        Expert expert = expertService.findById(1L);
        Opinion opinion = Opinion.builder()
                .id(2L)
                .description("That Was Great O1...")
                .expert(expert)
                .rate(3)
                .build();
        Opinion opinion2 = Opinion.builder()
                .id(3L)
                .expert(expert)
                .description("That Was Great O2...")
                .rate(4)
                .build();
        expert.setOpinions(List.of(opinion, opinion2));
        expertService.save(expert);
        opinionService.save(opinion);
        opinionService.save(opinion2);
        Double averagePoint = expertService.getAveragePoint(1L);
        assertEquals(averagePoint, 3.5);
    }

}