package com.rostami.onlineservice.dto.in;

import com.rostami.onlineservice.model.base.BaseEntity;

public interface BaseUpdateDto<T extends BaseEntity> {
    T convertToDomain(T fetchedEntity);
}
