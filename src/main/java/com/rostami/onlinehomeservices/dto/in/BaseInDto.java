package com.rostami.onlinehomeservices.dto.in;

import com.rostami.onlinehomeservices.model.base.BaseEntity;

public interface BaseInDto<T extends BaseEntity> {
    T convertToDomain();
}
