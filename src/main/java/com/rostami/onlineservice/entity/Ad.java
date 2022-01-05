package com.rostami.onlineservice.entity;


import com.rostami.onlineservice.entity.base.BaseEntity;
import com.rostami.onlineservice.entity.enums.AdStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @PrePersist
    private void currentDateOnCreate(){
        recordDate = Date.valueOf(LocalDate.now());
        recordTime = Time.valueOf(LocalTime.now());
    }

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

    @ToString.Exclude
    @ManyToOne(optional = false)
    private Customer customer;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ad",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Offer> offers;
    @ManyToOne
    @ToString.Exclude
    private SubServ subServ;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "ad")
    @ToString.Exclude
    private List<Opinion> opinions;
    @ManyToOne
    @ToString.Exclude
    private Expert chosenExpert;
}
