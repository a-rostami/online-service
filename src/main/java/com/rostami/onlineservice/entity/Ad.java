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
    @Column(nullable = false)
    private Date recordDate;
    @Column(nullable = false)
    private Time recordTime;
    @Column(nullable = false)
    private Date completionDate;
    @Column(nullable = false)
    private Time completionTime;
    @Column(precision = 17, scale = 2, nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String workDescription;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AdStatus status;

    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "ad", fetch = FetchType.EAGER)
    private List<Offer> offers;
    @ManyToOne
    private SubServ subServ;
}
