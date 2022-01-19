package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.in.create.CustomerCreateParam;
import com.rostami.onlineservice.dto.in.create.SubServCreateParam;
import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.entity.Ad;
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

    @Override
    public AdFindResult convertToDto(Ad entity) {
        id = entity.getId();
        completionDate = entity.getCompletionDate();
        completionTime = entity.getCompletionTime();
        price = entity.getPrice();
        workDescription = entity.getWorkDescription();
        address = entity.getAddress();
        return this;
    }
}
