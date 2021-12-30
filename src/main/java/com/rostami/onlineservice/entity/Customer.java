package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.abstracts.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @OneToOne(mappedBy = "customer", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Credit credit;
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
