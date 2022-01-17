package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Offer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@Builder
public class OfferCreateParam implements BaseInDto<Offer> {
    private Date startDate;
    private Time startTime;
    private Date completionDate;
    private Time completionTime;
    private BigDecimal price;
    ExpertCreateParam expertCreateParam;
    AdCreateParam adCreateParam;

    @Override
    public Offer convertToDomain() {
        return Offer.builder()
                .startDate(startDate)
                .startTime(startTime)
                .completionDate(completionDate)
                .completionTime(completionTime)
                .price(price)
                .expert(expertCreateParam.convertToDomain())
                .ad(adCreateParam.convertToDomain())
                .build();
    }
}
