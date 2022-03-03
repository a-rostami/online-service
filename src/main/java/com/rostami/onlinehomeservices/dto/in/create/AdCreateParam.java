package com.rostami.onlinehomeservices.dto.in.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rostami.onlinehomeservices.dto.in.BaseInDto;
import com.rostami.onlinehomeservices.model.Ad;
import com.rostami.onlinehomeservices.model.Customer;
import com.rostami.onlinehomeservices.model.SubServ;
import com.rostami.onlinehomeservices.model.enums.AdStatus;
import com.rostami.onlinehomeservices.exception.EntityRelationException;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdCreateParam implements BaseInDto<Ad> {
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

    private Long customerId;
    private Long subServId;

    @Override
    public Ad convertToDomain() {
        if (customerId == null)
            throw new EntityRelationException("Ad Should have a customer !");
        if (subServId == null)
            throw new EntityRelationException("Ad Should have a SubServ !");

        return Ad.builder()
                .completionDate(completionDate)
                .completionTime(completionTime)
                .price(price)
                .workDescription(workDescription)
                .address(address)
                .status(AdStatus.WAITING_FOR_OFFER)
                .customer(Customer.builder().id(customerId).build())
                .subServ(SubServ.builder().id(subServId).build())
                .build();
    }
}