package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@Entity
public class Offer extends BaseEntity {
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
    private Date startDate;
    @Column(nullable = false)
    private Time startTime;
    @Column(nullable = false)
    private Date completionDate;
    @Column(nullable = false)
    private Time completionTime;
    @Column(precision = 17, scale = 2)
    private BigDecimal price;

    @ManyToOne(optional = false)
    private Ad ad;

    @ManyToOne
    private Expert expert;
}
