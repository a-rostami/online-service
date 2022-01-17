package com.rostami.onlineservice.service.base;

import com.rostami.onlineservice.dto.in.BaseDto;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Setter
@Getter
public abstract class BaseService<T extends BaseEntity, ID extends Long> {
    private JpaRepository<T, ID> jpaRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult saveOrUpdate(BaseDto<T> dto){
        T saved = jpaRepository.save(dto.convertToDomain());
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    @Transactional
    public void deleteById(ID id){
        jpaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public T findById(ID id){
        return jpaRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<T> findAll(){
        return jpaRepository.findAll();
    }
}