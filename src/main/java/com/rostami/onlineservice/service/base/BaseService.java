package com.rostami.onlineservice.service.base;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.entity.base.BaseEntity;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.base.BaseRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public abstract class BaseService<T extends BaseEntity, ID extends Long> {
    private BaseRepository<T, ID> repository;
    private BaseOutDto<T, ?> baseOutDto;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult saveOrUpdate(BaseInDto<T> dto){
        T saved = repository.save(dto.convertToDomain());
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    @Transactional
    public BaseOutDto<T, ?> get(ID id){
        T entity = repository.findById(id).orElseThrow(() -> new EntityLoadException("There is no entity with this id"));
        return baseOutDto.convertToDto(entity);
    }

    @Transactional
    public List<BaseOutDto<T, ?>> list(){
        List<T> all = repository.findAll();
        return all.stream().map((entity) -> baseOutDto.convertToDto(entity)).collect(Collectors.toList());
    }

    @Transactional
    public void delete(ID id){
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll(Specification<T> specification){
        return repository.findAll(specification);
    }

    @Transactional(readOnly = true)
    public List<T> findAll(Specification<T> specification, Sort sort){
        return repository.findAll(specification, sort);
    }

    @Transactional(readOnly = true)
    public List<T> findAll(Specification<T> specification, Pageable pageable){
        return repository.findAll(specification, pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<T> findAll(Sort sort){
        return repository.findAll(sort);
    }

    @Transactional
    public long count(Specification<T> specification){
        return repository.count(specification);
    }
}