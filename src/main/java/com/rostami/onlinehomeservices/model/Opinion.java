package com.rostami.onlinehomeservices.model;

import com.rostami.onlinehomeservices.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private Integer rate;

    @Column(nullable = false)
    @NotNull
    private String description;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "expert_id", updatable = false)
    private Expert expert;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ad_id")
    private Ad ad;
}