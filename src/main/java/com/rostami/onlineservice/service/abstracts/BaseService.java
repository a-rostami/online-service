package com.rostami.onlineservice.service.abstracts;

import com.rostami.onlineservice.entity.abstracts.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Setter
@Getter
public abstract class BaseService<T extends BaseEntity, ID> {
    private JpaRepository<T, ID> jpaRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(T entity){
        jpaRepository.save(entity);
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
