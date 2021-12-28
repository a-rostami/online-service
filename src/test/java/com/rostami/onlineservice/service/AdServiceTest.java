package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SpringJUnitConfig(AppConfig.class)
class AdServiceTest {
    @Autowired
    private AdService adService;

    @Test
    void server_user_ok(){
    }
}