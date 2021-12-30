package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.abstracts.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@Entity
public class Offer extends BaseEntity {
    @Column(nullable = false)
    private Date recordDate;
    @Column(nullable = false)
    private Time recordTime;
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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Ad ad;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Expert expert;
}
