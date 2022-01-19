package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.Offer;
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
    // id can be null when there is no relation
    private Long id;
    private Date startDate;
    private Time startTime;
    private Date completionDate;
    private Time completionTime;
    private BigDecimal price;
    ExpertCreateParam expertCreateParam;
    AdCreateParam adCreateParam;

    @Override
    public Offer convertToDomain() {
        if (expertCreateParam == null || expertCreateParam.getId() == null)
            throw new EntityRelationException("Offer Should have Expert!");
        if (adCreateParam == null || adCreateParam.getId() == null)
            throw new EntityRelationException("Offer Should have ad!");
        return Offer.builder()
                .startDate(startDate)
                .startTime(startTime)
                .completionDate(completionDate)
                .completionTime(completionTime)
                .price(price)
                .expert(Expert.builder().id(expertCreateParam.getId()).build())
                .ad(Ad.builder().id(adCreateParam.getId()).build())
                .build();
    }
}
