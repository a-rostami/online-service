package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.abstracts.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@Entity
public class Customer extends User {
    @OneToOne(mappedBy = "customer", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Credit credit;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<Ad> ads;
}
