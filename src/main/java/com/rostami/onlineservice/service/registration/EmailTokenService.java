package com.rostami.onlineservice.service.registration;

import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.model.registration.EmailToken;
import com.rostami.onlineservice.repository.registration.EmailTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.rostami.onlineservice.util.ExceptionMessages.ENTITY_TOKEN_LOAD_MESSAGE;

@Service
@AllArgsConstructor
public class EmailTokenService {
    private final EmailTokenRepository repository;

    public CreateUpdateResult save(EmailToken confirmationToken){
        EmailToken saved = repository.save(confirmationToken);
        return CreateUpdateResult.builder()
                .id(saved.getId())
                .success(true)
                .build();
    }

    public EmailToken findByToken(String token){
        return repository.findByToken(token)
                .orElseThrow(() -> new EntityLoadException(ENTITY_TOKEN_LOAD_MESSAGE));
    }

    public void updateConfirmedAt(String token){
        repository.updateConfirmedAt(LocalDateTime.now(), token);
    }
}
