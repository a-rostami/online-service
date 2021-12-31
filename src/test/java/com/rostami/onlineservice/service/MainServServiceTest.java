package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.entity.MainServ;
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
class MainServServiceTest {
    @Autowired
    MainServService service;

    @Test
    void init_e_g(){
        MainServ mainServ = MainServ.builder()
                .id(1L)
                .name("Home Appliance")
                .build();
        MainServ mainServ2 = MainServ.builder()
                .id(2L)
                .name("Health Products")
                .build();
        service.save(mainServ);
        service.save(mainServ2);
    }

}