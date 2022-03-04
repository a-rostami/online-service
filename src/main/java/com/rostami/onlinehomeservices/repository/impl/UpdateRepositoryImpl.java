package com.rostami.onlinehomeservices.repository.impl;

import com.rostami.onlinehomeservices.dto.in.BaseUpdateDto;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.exception.EntityLoadException;
import com.rostami.onlinehomeservices.model.base.BaseEntity;
import lombok.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.ENTITY_ID_LOAD_MESSAGE;

@Repository
@RequiredArgsConstructor
@Setter
public class UpdateRepositoryImpl<T extends BaseEntity, ID extends Long> {

    @PersistenceContext
    private final EntityManager entityManager;

    private final UpdateCrudRepository<T, ID> repository;

    @Transactional
    public CreateUpdateResult update(BaseUpdateDto<T> updateDto, ID id){
        T fetchedEntity = getForUpdate(id);
        T saved = repository.save(updateDto.convertToDomain(fetchedEntity));
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    private T getForUpdate(ID id){
        T fetchedEntity = repository.findById(id).orElseThrow(() -> new EntityLoadException(ENTITY_ID_LOAD_MESSAGE));
        detach(fetchedEntity);
        return fetchedEntity;
    }

    private void detach(T entity){
        entityManager.detach(entity);
    }
}
