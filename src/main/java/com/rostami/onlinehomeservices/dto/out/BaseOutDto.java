package com.rostami.onlinehomeservices.dto.out;

import com.rostami.onlinehomeservices.model.base.BaseEntity;

public interface BaseOutDto<T extends BaseEntity, E extends BaseOutDto<T, E>>{
    E convertToDto(T entity);
}
