package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.entity.Customer;
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
                .firstname("arash")
                .lastname("rostami")
                .email("arash5@Gmail.com")
                .password("12345678")
                .username("mrrostami")
                .userStatus(UserStatus.NEW)
                .build();
        Customer customer2 = Customer.builder()
                .firstname("arash")
                .lastname("rostami")
                .email("arash5@Gmail.com")
                .password("56556128")
                .username("mrrostami")
                .userStatus(UserStatus.NEW)
                .build();
        customerService.save(customer);
        assertThrows(DuplicateEmailException.class, () -> customerService.save(customer2));
    }

    @Test
    void is_change_password_ok(){
        String newPassword = "123456789";
        Customer customer = Customer.builder()
                .id(1L)
                .firstname("arash")
                .lastname("rostami")
                .email("arash544@Gmail.com")
                .password("12345678")
                .username("mrrostami")
                .userStatus(UserStatus.NEW)
                .build();
        customerService.save(customer);
        customerService.changePassword(1L, newPassword);
        Customer entity = customerService.findByUserNameAndPassword("mrrostami", newPassword);
        assertEquals(entity, customer);
    }
}