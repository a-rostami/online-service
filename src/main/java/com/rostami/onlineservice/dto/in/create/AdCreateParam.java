package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseDto;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.enums.AdStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@Builder
public class AdCreateParam implements BaseDto<Ad> {
    private Date completionDate;
    private Time completionTime;
    private BigDecimal price;
    private String workDescription;
    private String address;
    private CustomerCreateParam customerCreateParam;
    private SubServCreateParam subServCreateParam;

    @Override
    public Ad convertToDomain() {
        return Ad.builder()
                .completionDate(completionDate)
                .completionTime(completionTime)
                .price(price)
                .workDescription(workDescription)
                .address(address)
                .status(AdStatus.WAITING_FOR_OFFER)
                .customer(customerCreateParam.convertToDomain())
                .subServ(subServCreateParam.convertToDomain())
                .build();
    }
}