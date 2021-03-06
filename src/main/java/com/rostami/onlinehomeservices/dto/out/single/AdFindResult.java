package com.rostami.onlinehomeservices.dto.out.single;

import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.model.Ad;
import com.rostami.onlinehomeservices.model.enums.AdStatus;
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
public class AdFindResult implements BaseOutDto<Ad, AdFindResult> {
    private Long id;
    private Date completionDate;
    private Time completionTime;
    private BigDecimal price;
    private String workDescription;
    private String address;
    private AdStatus adStatus;
    private ExpertFindResult chosenExpert;
    private SubServFindResult subServFindResult;

    @Override
    public AdFindResult convertToDto(Ad entity) {
        if (entity.getChosenExpert() != null)
            chosenExpert = ExpertFindResult.builder().build().convertToDto(entity.getChosenExpert());

        id = entity.getId();
        completionDate = entity.getCompletionDate();
        completionTime = entity.getCompletionTime();
        price = entity.getPrice();
        workDescription = entity.getWorkDescription();
        address = entity.getAddress();
        adStatus = entity.getStatus();
        subServFindResult = SubServFindResult.builder().build().convertToDto(entity.getSubServ());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdFindResult that = (AdFindResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
