package com.rostami.onlinehomeservices.model;

import com.rostami.onlinehomeservices.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Entity
public class MainServ extends BaseEntity {
    @Column(nullable = false, unique = true)
    @NotNull
    private String name;
}
