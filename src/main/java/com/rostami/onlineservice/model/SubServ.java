package com.rostami.onlineservice.model;

import com.rostami.onlineservice.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

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
    @JoinTable(name="expert_subServ",
            joinColumns=@JoinColumn(name="subServ_id"),
            inverseJoinColumns=@JoinColumn(name="expert_id")
    )
    private Set<Expert> experts;
}
