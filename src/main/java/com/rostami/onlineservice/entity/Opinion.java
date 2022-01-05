package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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

    @ManyToOne(optional = false)
    private Expert expert;

    @ManyToOne(optional = false)
    private Ad ad;
}