package com.rostami.onlineservice.dto.api.filter;

import com.rostami.onlineservice.model.enums.AdStatus;
import lombok.Data;

import java.sql.Date;

@Data
public class AdFilter {
    private AdStatus adStatus;
    private Long subServId;
    private Date fromDate;
    private Date toDate;
}
