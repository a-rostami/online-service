package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "expert", fetch = FetchType.EAGER)
    private Set<Opinion> opinions;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private transient List<Offer> offers;

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
