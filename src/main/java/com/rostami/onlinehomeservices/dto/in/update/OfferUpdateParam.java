package com.rostami.onlinehomeservices.dto.in.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rostami.onlinehomeservices.dto.in.BaseUpdateDto;
import com.rostami.onlinehomeservices.model.Offer;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferUpdateParam implements BaseUpdateDto<Offer> {
    @NotNull
    private Long id;
    @NotNull
    private Date startDate;
    @NotNull
    private Time startTime;
    @NotNull
    private Date completionDate;
    @NotNull
    private Time completionTime;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;

    @Override
    public Offer convertToDomain(Offer fetchedEntity) {
        fetchedEntity.setId(id);
        fetchedEntity.setStartDate(startDate);
        fetchedEntity.setStartTime(startTime);
        fetchedEntity.setCompletionDate(completionDate);
        fetchedEntity.setCompletionTime(completionTime);
        fetchedEntity.setPrice(price);
        return fetchedEntity;
    }
}
