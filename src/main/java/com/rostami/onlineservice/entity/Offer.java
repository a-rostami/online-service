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
    private Date recordDate;
    private Time recordTime;
    private Date startDate;
    private Time startTime;
    private Date completionDate;
    private Time completionTime;
    @Column(precision = 17, scale = 2)
    private BigDecimal price;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Ad ad;
}
