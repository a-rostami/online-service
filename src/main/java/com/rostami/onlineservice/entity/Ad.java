package com.rostami.onlineservice.entity;


import com.rostami.onlineservice.entity.base.BaseEntity;
import com.rostami.onlineservice.entity.enums.AdStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@Entity
public class Ad extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private Date recordDate;
    @Column(nullable = false, updatable = false)
    private Time recordTime;

    @PrePersist
    private void currentDateOnCreate(){
        recordDate = Date.valueOf(LocalDate.now());
        recordTime = Time.valueOf(LocalTime.now());
    }

    @Column(nullable = false)
    @NotNull
    private Date completionDate;

    @Column(nullable = false)
    @NotNull
    private Time completionTime;

    @Column(precision = 17, scale = 2, nullable = false)
    @NotNull
    private BigDecimal price;

    @Column(nullable = false)
    @NotNull
    private String workDescription;

    @Column(nullable = false)
    @NotNull
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private AdStatus status;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "subServ_id")
    private SubServ subServ;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "chosenExpert_id")
    private Expert chosenExpert;
}
