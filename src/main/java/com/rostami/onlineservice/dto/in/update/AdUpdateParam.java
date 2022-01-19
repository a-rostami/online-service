package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Ad;
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
public class AdUpdateParam implements BaseInDto<Ad> {
    @NotNull
    private Long id;
    private Date completionDate;
    private Time completionTime;
    private BigDecimal price;
    private String workDescription;
    private String address;

    @Override
    public Ad convertToDomain() {
        return Ad.builder()
                .id(id)
                .completionDate(completionDate)
                .completionTime(completionTime)
                .price(price)
                .workDescription(workDescription)
                .address(address)
                .build();
    }
}
