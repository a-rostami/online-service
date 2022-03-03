package com.rostami.onlinehomeservices.repository;

import com.rostami.onlinehomeservices.model.Credit;
import com.rostami.onlinehomeservices.model.Customer;
import com.rostami.onlinehomeservices.model.enums.UserStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void test_enable_customer_isOk() {
        // given ----------------------------------------

        String email = "james@gmail.com";
        Customer customer = Customer.builder()
                .firstname("James")
                .lastname("Bond")
                .password("@Test1234")
                .email(email)
                .userStatus(UserStatus.PENDING)
                .isEnable(false)
                .isNonLocked(true)
                .credit(Credit.builder().balance(BigDecimal.valueOf(0)).build())
                .build();
        customerRepository.saveAndFlush(customer);

        // when ----------------------------------------

        int updatedRows = customerRepository.enableCustomer(email);
        Customer fetchedCustomer = customerRepository.findByEmail(email).orElse(Customer.builder().build());

        // then ----------------------------------------

        assertThat(fetchedCustomer.isEnable()).isEqualTo(true);
        assertThat(updatedRows).isGreaterThan(0);
    }
}