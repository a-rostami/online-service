package com.rostami.onlineservice.dto.out;

import com.rostami.onlineservice.model.base.BaseEntity;

public interface BaseOutDto<T extends BaseEntity, E extends BaseOutDto<T, E>>{
    E convertToDto(T entity);
}
