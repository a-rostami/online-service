package com.rostami.onlinehomeservices.dto.api.filter;

import com.rostami.onlinehomeservices.model.enums.AdStatus;
import lombok.Data;

import java.sql.Date;

@Data
public class AdFilter {
    private AdStatus adStatus;
    private Long subServId;
    private Date fromDate;
    private Date toDate;
}
