package com.rostami.onlinehomeservices.model;

import com.rostami.onlinehomeservices.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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
}
