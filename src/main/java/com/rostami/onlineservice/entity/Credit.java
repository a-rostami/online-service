package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@Entity
public class Credit extends BaseEntity {
    @Column(nullable = false)
    @NotNull
    private BigDecimal balance;

    @ToString.Exclude
    @OneToOne(cascade = {CascadeType.MERGE})
    @NotNull
    private Expert expert;

    @ToString.Exclude
    @OneToOne( cascade = {CascadeType.MERGE})
    @NotNull
    private Customer customer;
}
