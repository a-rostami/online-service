package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.repository.ExpertRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


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
        // expert only have two opinion with rates : 3 , 4
        ExpertFindResult expertDto = (ExpertFindResult) expertService.get(4L);
        Expert expert = Expert.builder().id(expertDto.getId()).build();
        Double averagePoint = expertService.getAveragePoint(expert);
        assertEquals(averagePoint, 3.5);
    }

}