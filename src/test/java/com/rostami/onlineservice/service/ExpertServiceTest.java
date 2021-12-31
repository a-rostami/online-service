package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.enums.Role;
import com.rostami.onlineservice.entity.enums.UserStatus;
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

    /*@Test
    void init_e_g(){
        Expert expert = Expert.builder()
                .id(1L)
                .firstname("arash")
                .lastname("rostami")
                .email("test@Gmail.com")
                .password("12345678")
                .username("mrrostami")
                .role(Role.EXPERT)
                .userStatus(UserStatus.NEW)
                .avatar(new byte[10])
                .build();
        expertService.save(expert);
    }*/

}