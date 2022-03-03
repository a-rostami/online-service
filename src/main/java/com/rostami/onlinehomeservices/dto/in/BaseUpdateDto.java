package com.rostami.onlinehomeservices.dto.in;

import com.rostami.onlinehomeservices.model.base.BaseEntity;

public interface BaseUpdateDto<T extends BaseEntity> {
    T convertToDomain(T fetchedEntity);
}
