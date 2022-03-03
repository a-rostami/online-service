package com.rostami.onlinehomeservices.repository.security;

import com.rostami.onlinehomeservices.model.security.auth.Permission;
import com.rostami.onlinehomeservices.model.security.enums.PermissionEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void test_findBy_PermissionEnum_isOk() {
        // given ---------------------------------------------------------
        PermissionEnum permissionEnum = PermissionEnum.AD_READ;
        Permission permission = Permission.builder()
                .permissionEnum(permissionEnum)
                .build();

        // when ----------------------------------------------------------
        Permission saved = repository.save(permission);
        Permission fetched = repository.findByPermissionEnum(permissionEnum).orElse(Permission.builder().build());

        // then ----------------------------------------------------------
        assertThat(saved).isEqualTo(fetched);
    }
}