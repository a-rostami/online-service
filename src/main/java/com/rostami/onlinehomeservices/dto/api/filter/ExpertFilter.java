package com.rostami.onlinehomeservices.dto.api.filter;

import com.rostami.onlinehomeservices.dto.api.filter.base.UserFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExpertFilter extends UserFilter {
    private Long subServId;
}
