package com.rostami.onlineservice.service.registration;

import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.model.registration.EmailConfirmationToken;
import com.rostami.onlineservice.repository.registration.EmailConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EmailConfirmationTokenService {
    private final EmailConfirmationTokenRepository repository;

    public CreateUpdateResult save(EmailConfirmationToken confirmationToken){
        EmailConfirmationToken saved = repository.save(confirmationToken);
        return CreateUpdateResult.builder()
                .id(saved.getId())
                .success(true)
                .build();
    }

    public EmailConfirmationToken findByToken(String token){
        return repository.findByToken(token)
                .orElseThrow(() -> new EntityLoadException("There Is No Email Confirmation Token With THis ID"));
    }

    public int updateConfirmedAt(String token){
        return repository.updateConfirmedAt(LocalDateTime.now(), token);
    }
}
