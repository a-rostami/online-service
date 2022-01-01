package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.SubServ;
import com.rostami.onlineservice.entity.enums.AdStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@SpringJUnitConfig({AppConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdServiceTest {
    @Autowired
    AdService adService;

    @Autowired
    CustomerService customerService;

    @Autowired
    SubServService subServService;

    @Test
    void add_newAd_isOk() throws ParseException {
        Customer customer = customerService.findById(1L);
        SubServ subServ = subServService.findById(1L);
        Ad ad = Ad.builder()
                .id(1L)
                .address("Tabriz")
                .completionDate(Date.valueOf("2021-12-30"))
                .completionTime(Time.valueOf("21:00:00"))
                .price(BigDecimal.valueOf(100000L))
                .recordTime(Time.valueOf("24:00:00"))
                .recordDate(Date.valueOf("2022-01-31"))
                .workDescription("I Want You To Do SomeThing Good")
                .status(AdStatus.WAITING_FOR_OFFER)
                .customer(customer)
                .subServ(subServ)
                .build();
        customer.setAds(List.of(ad));
        adService.save(ad);
        customerService.save(customer);
        Ad entity = customerService.findById(1L).getAds().get(0);
        assertEquals(ad, entity);
    }

}