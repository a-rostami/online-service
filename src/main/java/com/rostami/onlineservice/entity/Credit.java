package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.abstracts.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@Entity
public class Credit extends BaseEntity {
    @Column(nullable = false)
    private BigDecimal balance;

    @OneToOne
    private Expert expert;

    @OneToOne
    private Customer customer;
}
