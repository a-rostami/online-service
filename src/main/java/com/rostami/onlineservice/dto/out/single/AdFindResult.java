package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.in.create.CustomerCreateParam;
import com.rostami.onlineservice.dto.in.create.SubServCreateParam;
import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.entity.Ad;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@Builder
public class AdFindResult implements BaseOutDto<Ad, AdFindResult> {
    private Date completionDate;
    private Time completionTime;
    private BigDecimal price;
    private String workDescription;
    private String address;

    @Override
    public AdFindResult convertToDto(Ad entity) {
        completionDate = entity.getCompletionDate();
        completionTime = entity.getCompletionTime();
        price = entity.getPrice();
        workDescription = entity.getWorkDescription();
        address = entity.getAddress();
        return this;
    }
}
