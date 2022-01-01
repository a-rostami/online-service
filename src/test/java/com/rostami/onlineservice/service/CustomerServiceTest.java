package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.enums.Role;
import com.rostami.onlineservice.entity.enums.UserStatus;
import com.rostami.onlineservice.exception.DuplicateEmailException;
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
class CustomerServiceTest {
    @Autowired
    CustomerService customerService;

    @Test
    void duplicate_email_is_throws_exception(){
        Customer customer = Customer.builder()
                .id(2L)
                .firstname("arash")
                .lastname("rostami")
                // this email exists in DB
                .email("test@Gmail.com")
                .password("56556128")
                .username("mrrostami")
                .role(Role.CUSTOMER)
                .userStatus(UserStatus.NEW)
                .build();
        assertThrows(DuplicateEmailException.class, () -> customerService.save(customer));
    }

    @Test
    void is_change_password_ok(){
        String newPassword = "123456789";
        Customer customer = Customer.builder()
                .id(1L)
                .firstname("arash")
                .lastname("rostami")
                .email("test1@Gmail.com")
                .password("12345678")
                .username("mrrostami")
                .role(Role.CUSTOMER)
                .userStatus(UserStatus.NEW)
                .build();
        customerService.changePassword(1L, newPassword);
        Customer entity = customerService.findByUserNameAndPassword("mrrostami", newPassword);
        assertEquals(entity, customer);
    }
}