package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Entity
public class SubServ extends BaseEntity {
    @Column(nullable = false)
    @NotNull
    private String name;
    @Column(nullable = false)
    @NotNull
    private BigDecimal basePrice;


    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "mainServ_id")
    private MainServ mainServ;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn
    private List<Expert> experts;
}
