package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Entity
public class Customer extends User {

    @ToString.Exclude
    @OneToOne(mappedBy = "customer", cascade = {CascadeType.MERGE})
    private Credit credit;
}