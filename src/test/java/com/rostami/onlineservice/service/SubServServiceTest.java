package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.entity.SubServ;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;

@DataJpaTest
@ActiveProfiles("test")
@SpringJUnitConfig({AppConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SubServServiceTest {
    @Autowired
    SubServService service;

    @Autowired
    MainServService mainService;

    @Test
    void init_e_g(){
        SubServ subServ = SubServ.builder()
                .id(1L)
                .name("Kitchen Stuff")
                .basePrice(BigDecimal.valueOf(120000L))
                .mainServ(mainService.findById(1L))
                .build();
        SubServ subServ2 = SubServ.builder()
                .id(2L)
                .name("bathroom")
                .basePrice(BigDecimal.valueOf(180000L))
                .mainServ(mainService.findById(2L))
                .build();
        service.save(subServ);
        service.save(subServ2);
    }

}