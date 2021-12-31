package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.abstracts.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Expert extends User {
    @Lob
    @Column( columnDefinition = "BLOB", nullable = false)
    private byte[] avatar;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<SubServ> subServs;

    @OneToOne(mappedBy = "expert", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Credit credit;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Opinion> opinions;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Offer> offers;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "firstname = " + getFirstname() + ", " +
                "lastname = " + getLastname() + ", " +
                "username = " + getUsername() + ", " +
                "email = " + getEmail() + ", " +
                "password = " + getPassword() + ", " +
                "role = " + getRole() + ", " +
                "userStatus = " + getUserStatus() + ")";
    }
}
