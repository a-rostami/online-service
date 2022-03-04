package com.rostami.onlinehomeservices.service.base;

import com.rostami.onlinehomeservices.dto.in.BaseInDto;
import com.rostami.onlinehomeservices.dto.in.BaseUpdateDto;
import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.model.base.BaseEntity;
import com.rostami.onlinehomeservices.exception.EntityLoadException;
import com.rostami.onlinehomeservices.repository.base.BaseRepository;
import com.rostami.onlinehomeservices.repository.impl.UpdateRepositoryImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.ENTITY_ID_LOAD_MESSAGE;

@Setter
@Getter
public class BaseService<T extends BaseEntity, ID extends Long, E extends BaseOutDto<T, E>> {
    private BaseRepository<T, ID> repository;
    private BaseOutDto<T, E> baseOutDto;
    private UpdateRepositoryImpl<T, ID> updateRepositoryImpl;

    @Transactional
    public CreateUpdateResult save(BaseInDto<T> dto){
        T saved = repository.save(dto.convertToDomain());
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    @Transactional
    public CreateUpdateResult update(BaseUpdateDto<T> updateDto, ID id){
        return updateRepositoryImpl.update(updateDto, id);
    }

    @Transactional
    public BaseOutDto<T, E> findById(ID id){
        T entity = repository.findById(id).orElseThrow(() -> new EntityLoadException(ENTITY_ID_LOAD_MESSAGE));
        return baseOutDto.convertToDto(entity);
    }

    @Transactional
    public Set<BaseOutDto<T, E>> findAll(){
        List<T> all = repository.findAll();
        return all.stream().map((entity) -> baseOutDto.convertToDto(entity)).collect(Collectors.toSet());
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

    @Transactional
    public long count(Specification<T> specification){
        return repository.count(specification);
    }
}