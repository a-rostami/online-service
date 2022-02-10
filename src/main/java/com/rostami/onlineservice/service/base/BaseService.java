package com.rostami.onlineservice.service.base;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.model.base.BaseEntity;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.base.BaseRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
public abstract class BaseService<T extends BaseEntity, ID extends Long, E extends BaseOutDto<T, E>> {
    private BaseRepository<T, ID> repository;
    private BaseOutDto<T, E> baseOutDto;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult save(BaseInDto<T> dto){
        T saved = repository.save(dto.convertToDomain());
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult update(T entity){
        T saved = repository.save(entity);
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    @Transactional
    public BaseOutDto<T, E> get(ID id){
        T entity = repository.findById(id).orElseThrow(() -> new EntityLoadException("There is no model with this id"));
        return baseOutDto.convertToDto(entity);
    }

    @Transactional
    public T getForUpdate(ID id){
        T fetchedEntity = repository.findById(id).orElseThrow(() -> new EntityLoadException("There is no model with this id"));
        detach(fetchedEntity);
        return fetchedEntity;
    }

    @Transactional
    public Set<BaseOutDto<T, E>> list(){
        List<T> all = repository.findAll();
        return all.stream().map((entity) -> baseOutDto.convertToDto(entity)).collect(Collectors.toSet());
    }

    @Transactional
    public void detach(T entity){
        entityManager.detach(entity);
    }

    @Transactional
    public void delete(ID id){
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Set<BaseOutDto<T, E>> findAll(Specification<T> specification){
        List<T> entities = repository.findAll(specification);
        return entities.stream().map(entity -> baseOutDto.convertToDto(entity)).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<BaseOutDto<T, E>> findAll(Specification<T> specification, Sort sort){
        List<T> entities = repository.findAll(specification, sort);
        return entities.stream().map(entity -> baseOutDto.convertToDto(entity)).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<BaseOutDto<T, E>> findAll(Specification<T> specification, Pageable pageable){
        List<T> entities = repository.findAll(specification, pageable).getContent();
        return entities.stream().map(entity -> baseOutDto.convertToDto(entity)).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<BaseOutDto<T, E>> findAll(Sort sort){
        List<T> entities = repository.findAll(sort);
        return entities.stream().map(entity -> baseOutDto.convertToDto(entity)).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<BaseOutDto<T, E>> findAll(Pageable pageable){
        List<T> entities = repository.findAll(pageable).getContent();
        return entities.stream().map(entity -> baseOutDto.convertToDto(entity)).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public BaseOutDto<T, E> findOne(Specification<T> specification){
        T entity = repository.findOne(specification)
                .orElseThrow(() -> new EntityLoadException("There is no model with this id"));
        return baseOutDto.convertToDto(entity);
    }

    @Transactional
    public long count(Specification<T> specification){
        return repository.count(specification);
    }
}