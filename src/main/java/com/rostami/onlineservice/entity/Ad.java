package com.rostami.onlineservice.entity;


import com.rostami.onlineservice.entity.abstracts.BaseEntity;
import com.rostami.onlineservice.entity.enums.AdStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@Entity
public class Ad extends BaseEntity {
    private Date recordDate;
    private Time recordTime;
    private Date completionDate;
    private Time completionTime;
    @Column(precision = 17, scale = 2)
    private BigDecimal price;
    private String workDescription;
    private String address;
    @Enumerated(EnumType.STRING)
    private AdStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "ad", fetch = FetchType.EAGER)
    private List<Offer> offers;
    @ManyToOne
    private SubServ subServ;

}
