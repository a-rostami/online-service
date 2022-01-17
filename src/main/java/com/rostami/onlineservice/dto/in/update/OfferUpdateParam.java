package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Offer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@Builder
public class OfferUpdateParam implements BaseInDto<Offer> {
    @NotNull
    private Long id;
    private Date startDate;
    private Time startTime;
    private Date completionDate;
    private Time completionTime;
    private BigDecimal price;

    @Override
    public Offer convertToDomain() {
        return Offer.builder()
                .id(id)
                .startDate(startDate)
                .startTime(startTime)
                .completionDate(completionDate)
                .completionTime(completionTime)
                .price(price)
                .build();
    }
}
