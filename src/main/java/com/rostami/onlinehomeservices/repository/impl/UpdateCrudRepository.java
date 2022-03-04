package com.rostami.onlinehomeservices.repository.impl;

import com.rostami.onlinehomeservices.model.base.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateCrudRepository<T extends BaseEntity, ID extends  Long> extends CrudRepository<T, ID> {
}
