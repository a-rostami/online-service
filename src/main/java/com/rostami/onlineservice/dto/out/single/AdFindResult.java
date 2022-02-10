package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.model.Ad;
import com.rostami.onlineservice.model.enums.AdStatus;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

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
}
