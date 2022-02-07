package com.rostami.onlineservice.repository.registration;

import com.rostami.onlineservice.model.registration.EmailConfirmationToken;
import com.rostami.onlineservice.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailConfirmationTokenRepository extends BaseRepository<EmailConfirmationToken, Long> {
    Optional<EmailConfirmationToken> findByToken(String token);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE EmailConfirmationToken ec SET ec.confirmedAt = ?1 WHERE ec.token = ?2")
    int updateConfirmedAt(LocalDateTime confirmedAt, String token);
}