package com.rostami.onlinehomeservices.repository.registration;

import com.rostami.onlinehomeservices.model.registration.EmailToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class EmailTokenRepositoryTest {

    @Autowired
    private EmailTokenRepository repository;

    @AfterEach
    void tearDown(){
        repository.deleteAll();
    }

    @Test
    void test_update_confirmedAt_isOk() {
        // given ----------------------------------------------------------
       UUID token = UUID.randomUUID();
        LocalDateTime confirmedAt = LocalDateTime.now().plusMinutes(1);

        EmailToken emailToken = EmailToken.builder()
                .token(token.toString())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();

        // when -----------------------------------------------------------
        repository.save(emailToken);
        repository.updateConfirmedAt(confirmedAt, token.toString());
        EmailToken byToken = repository.findByToken(token.toString()).orElse(EmailToken.builder().build());

        // then -----------------------------------------------------------
        assertThat(byToken.getConfirmedAt()).isEqualTo(confirmedAt);
    }

    @Test
    void test_findByToken_isOk() {
        // given ----------------------------------------------------------
        UUID token = UUID.randomUUID();

        EmailToken emailToken = EmailToken.builder()
                .token(token.toString())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();


        // when -----------------------------------------------------------
        EmailToken saved = repository.save(emailToken);
        EmailToken byToken = repository.findByToken(token.toString()).orElse(EmailToken.builder().build());


        // then -----------------------------------------------------------
        assertThat(saved).isEqualTo(byToken);
    }
}