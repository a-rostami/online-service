package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.BaseEntity;
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
public class Offer extends BaseEntity {
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
    private Date startDate;
    @Column(nullable = false)
    @NotNull
    private Time startTime;
    @Column(nullable = false)
    @NotNull
    private Date completionDate;
    @Column(nullable = false)
    @NotNull
    private Time completionTime;
    @Column(precision = 17, scale = 2)
    @NotNull
    private BigDecimal price;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "expert_id")
    private Expert expert;
}
