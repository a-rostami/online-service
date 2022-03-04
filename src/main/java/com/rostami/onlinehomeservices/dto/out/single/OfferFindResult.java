package com.rostami.onlinehomeservices.dto.out.single;

import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.model.Offer;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferFindResult implements BaseOutDto<Offer, OfferFindResult> {
    private Long id;
    private Date startDate;
    private Time startTime;
    private Date completionDate;
    private Time completionTime;
    private BigDecimal price;

    @Override
    public OfferFindResult convertToDto(Offer entity) {
        id = entity.getId();
        startDate = entity.getStartDate();
        startTime = entity.getStartTime();
        completionDate = entity.getCompletionDate();
        completionTime = entity.getCompletionTime();
        price = entity.getPrice();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferFindResult that = (OfferFindResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
