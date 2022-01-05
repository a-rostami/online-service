package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@Entity
public class Opinion extends BaseEntity {
    @Column(nullable = false)
    @Max(value = 5)
    @Min(value = 1)
    private Integer rate;
    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Expert expert;

    @ManyToOne
    private Ad ad;
}