package com.rostami.onlinehomeservices.repository;

import com.rostami.onlinehomeservices.model.Credit;
import com.rostami.onlinehomeservices.model.Expert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static com.rostami.onlinehomeservices.model.enums.UserStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ExpertRepositoryTest {

    @Autowired
    private ExpertRepository expertRepository;

    @AfterEach
    void tearDown() {
        expertRepository.deleteAll();
    }

    @Test
    void test_enable_expert_isOk() {
        // given -------------------------------------

        String email = "arash@gmail.com";
        Expert expert = Expert.builder()
                .firstname("arash")
                .lastname("rostami")
                .password("@Test1234")
                .email(email)
                .avatar(new byte[10])
                .userStatus(PENDING)
                .isEnable(false)
                .isNonLocked(false)
                .credit(Credit.builder().balance(BigDecimal.valueOf(0)).build())
                .build();
        expertRepository.saveAndFlush(expert);

        // when -------------------------------------

        int updatedRows = expertRepository.enableExpert(email);
        Expert fetchedExpert = expertRepository.findByEmail(email).orElse(Expert.builder().build());

        // then -------------------------------------

        assertThat(fetchedExpert.isEnable()).isEqualTo(true);
        assertThat(updatedRows).isGreaterThan(0);
    }

    @Test
    void test_unlock_expert_isOk() {
        // given -------------------------------------

        String email = "arash@gmail.com";
        Expert expert = Expert.builder()
                .firstname("arash")
                .lastname("rostami")
                .password("@Test1234")
                .email(email)
                .avatar(new byte[10])
                .userStatus(PENDING)
                .isEnable(false)
                .isNonLocked(false)
                .credit(Credit.builder().balance(BigDecimal.valueOf(0)).build())
                .build();
        expertRepository.saveAndFlush(expert);

        // when -------------------------------------

        int updatedRows = expertRepository.unlockExpert(email);
        Expert fetchedExpert = expertRepository.findByEmail(email).orElse(Expert.builder().build());

        // then -------------------------------------

        assertThat(fetchedExpert.isNonLocked()).isEqualTo(true);
        assertThat(updatedRows).isGreaterThan(0);
    }
}