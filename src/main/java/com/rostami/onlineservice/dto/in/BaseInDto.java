package com.rostami.onlineservice.dto.in;

import com.rostami.onlineservice.entity.base.BaseEntity;

public interface BaseInDto<T extends BaseEntity> {
    T convertToDomain();
}
