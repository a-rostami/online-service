package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.SubServ;
import com.rostami.onlineservice.entity.enums.AdStatus;
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
public class AdCreateParam implements BaseInDto<Ad> {
    // id can be null when there is no relation
    private Long id;
    private Date completionDate;
    private Time completionTime;
    private BigDecimal price;
    private String workDescription;
    private String address;
    private CustomerCreateParam customerCreateParam;
    private SubServCreateParam subServCreateParam;

    @Override
    public Ad convertToDomain() {
        if (customerCreateParam == null || customerCreateParam.getId() == null)
            throw new EntityRelationException("Ad Should have a customer !");
        if (subServCreateParam == null || subServCreateParam.getId() == null)
            throw new EntityRelationException("Ad Should have a SubServ !");

        return Ad.builder()
                .completionDate(completionDate)
                .completionTime(completionTime)
                .price(price)
                .workDescription(workDescription)
                .address(address)
                .status(AdStatus.WAITING_FOR_OFFER)
                .customer(Customer.builder().id(customerCreateParam.getId()).build())
                .subServ(SubServ.builder().id(subServCreateParam.getId()).build())
                .build();
    }
}