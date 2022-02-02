package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.dto.in.create.CustomerCreateParam;
import com.rostami.onlineservice.model.Customer;
import com.rostami.onlineservice.exception.DuplicatedEmailException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
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


   /* @Test
    void duplicate_email_is_throws_exception(){
        CustomerCreateParam customer = CustomerCreateParam.builder()
                .firstname("arash")
                .lastname("rostami")
                // this email exists in DB
                .email("test@Gmail.com")
                .password("56556128")
                .username("mrrostami")
                .build();
        assertThrows(DuplicatedEmailException.class, () -> customerService.saveOrUpdate(customer));
    }*/

    /*@Test
    void is_change_password_ok(){
        String newPassword = "123456789";
        String username = "mrrostami";
        CustomerFindResult customerDto = (CustomerFindResult) customerService.get(1L);
        Customer customer = Customer.builder().id(customerDto.getId()).build();
        customerService.changePassword(customer.getId(), newPassword);
        // username of found model is : mrrostami
        Customer newPassEntity = customerService.findAll(usernamePassSpecification(username, newPassword)).get(0);
        assertEquals(newPassEntity, customer);
    }*/

    static Specification<Customer> usernamePassSpecification(String username, String password){
        return ((root, cq, cb) ->
                cb.and(cb.equal(root.get("username"), username), cb.equal(root.get("password"), password)));
    }
}