package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.abstracts.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@Entity
public class Opinion extends BaseEntity {
    private int rate;
    private String description;

    @ManyToOne
    private Expert expert;
}
