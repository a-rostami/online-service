package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Customer extends User {

    @ToString.Exclude
    @OneToOne(mappedBy = "customer", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Credit credit;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<Ad> ads;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "firstname = " + getFirstname() + ", " +
                "lastname = " + getLastname() + ", " +
                "username = " + getUsername() + ", " +
                "email = " + getEmail() + ", " +
                "password = " + getPassword() + ", " +
                "role = " + getRole() + ", " +
                "userStatus = " + getUserStatus() + ")";
    }
}
