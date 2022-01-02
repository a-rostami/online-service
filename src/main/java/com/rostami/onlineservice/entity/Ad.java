package com.rostami.onlineservice.entity;


import com.rostami.onlineservice.entity.base.BaseEntity;
import com.rostami.onlineservice.entity.enums.AdStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
@SuperBuilder
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

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Customer customer;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ad",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Offer> offers;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private SubServ subServ;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "ad")
    private List<Opinion> opinions;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Expert chosenExpert;
}
