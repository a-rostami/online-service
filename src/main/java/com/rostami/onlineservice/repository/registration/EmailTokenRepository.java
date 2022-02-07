package com.rostami.onlineservice.repository.registration;

import com.rostami.onlineservice.model.registration.EmailToken;
import com.rostami.onlineservice.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailTokenRepository extends BaseRepository<EmailToken, Long> {
    Optional<EmailToken> findByToken(String token);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE EmailToken ec SET ec.confirmedAt = ?1 WHERE ec.token = ?2")
    void updateConfirmedAt(LocalDateTime confirmedAt, String token);
}