package com.rostami.onlineservice.service.base;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.entity.base.BaseEntity;
import com.rostami.onlineservice.exception.EntityLoadException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public abstract class BaseService<T extends BaseEntity, ID extends Long> {
    private JpaRepository<T, ID> jpaRepository;
    private BaseOutDto<T, ?> baseOutDto;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult saveOrUpdate(BaseInDto<T> dto){
        T saved = jpaRepository.save(dto.convertToDomain());
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    @Transactional(readOnly = true)
    public BaseOutDto<T, ?> get(ID id){
        T entity = jpaRepository.findById(id).orElseThrow(() -> new EntityLoadException("There is no entity with this id"));
        return baseOutDto.convertToDto(entity);
    }

    @Transactional(readOnly = true)
    public List<BaseOutDto<T, ?>> list(){
        List<T> all = jpaRepository.findAll();
        return all.stream().map((entity) -> baseOutDto.convertToDto(entity)).collect(Collectors.toList());
    }

    @Transactional
    public void delete(ID id){
        jpaRepository.deleteById(id);
    }

}