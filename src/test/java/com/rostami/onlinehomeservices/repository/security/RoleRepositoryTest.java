package com.rostami.onlinehomeservices.repository.security;

import com.rostami.onlinehomeservices.model.security.auth.Role;
import com.rostami.onlinehomeservices.model.security.enums.RoleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void test_findBy_RoleEnum_isOk() {
        // given ----------------------------------------------------------
        RoleEnum roleEnum = RoleEnum.CUSTOMER;
        Role role = Role.builder()
                .roleEnum(roleEnum)
                .build();

        // when -----------------------------------------------------------
        Role saved = repository.save(role);
        Role fetchedRole = repository.findByRoleEnum(roleEnum).orElse(Role.builder().build());

        // then -----------------------------------------------------------
        assertThat(saved).isEqualTo(fetchedRole);
    }
}