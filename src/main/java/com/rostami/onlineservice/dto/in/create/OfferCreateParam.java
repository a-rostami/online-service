package com.rostami.onlineservice.dto.in.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.Ad;
import com.rostami.onlineservice.model.Expert;
import com.rostami.onlineservice.model.Offer;
import com.rostami.onlineservice.exception.EntityRelationException;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferCreateParam implements BaseInDto<Offer> {
    private Date startDate;
    private Time startTime;
    private Date completionDate;
    private Time completionTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;
    private Long expertId;
    private Long adId;

    @Override
    public Offer convertToDomain() {
        if (expertId == null)
            throw new EntityRelationException("Offer Should have Expert!");
        if (adId == null)
            throw new EntityRelationException("Offer Should have ad!");
        return Offer.builder()
                .startDate(startDate)
                .startTime(startTime)
                .completionDate(completionDate)
                .completionTime(completionTime)
                .price(price)
                .expert(Expert.builder().id(expertId).build())
                .ad(Ad.builder().id(adId).build())
                .build();
    }
}
