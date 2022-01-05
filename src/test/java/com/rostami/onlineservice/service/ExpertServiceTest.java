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
        Expert expert = expertService.findById(4L);
        // rate of opinion is : 3
        Opinion opinion = opinionService.findById(22L);
        // rate of opinion is : 4
        Opinion opinion2 = opinionService.findById(23L);
        expert.setOpinions(List.of(opinion, opinion2));
        expertService.save(expert);
        Double averagePoint = expertService.getAveragePoint(expert);
        assertEquals(averagePoint, 3.5);
    }

}