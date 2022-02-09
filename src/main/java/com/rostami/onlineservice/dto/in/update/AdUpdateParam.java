package com.rostami.onlineservice.dto.in.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rostami.onlineservice.dto.in.BaseUpdateDto;
import com.rostami.onlineservice.model.Ad;
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
public class AdUpdateParam implements BaseUpdateDto<Ad> {
    @NotNull
    private Long id;
    @NotNull
    private Date completionDate;
    @NotNull
    private Time completionTime;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;
    @NotNull
    private String workDescription;
    @NotNull
    private String address;

    @Override
    public Ad convertToDomain(Ad fetchedEntity) {
        fetchedEntity.setId(id);
        fetchedEntity.setCompletionDate(completionDate);
        fetchedEntity.setPrice(price);
        fetchedEntity.setWorkDescription(workDescription);
        fetchedEntity.setAddress(address);
        return fetchedEntity;
    }
}
