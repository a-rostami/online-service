package com.rostami.onlineservice.entity.base;

import com.rostami.onlineservice.entity.Credit;
import com.rostami.onlineservice.entity.enums.Role;
import com.rostami.onlineservice.entity.enums.UserStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User extends BaseEntity {
    @Column(nullable = false)
    @NotNull
    private String firstname;

    @Column(nullable = false)
    @NotNull
    private String lastname;

    @Column(nullable = false)
    @NotNull
    private String username;

    @Email
    @Column(nullable = false, unique = true)
    @NotNull
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    @NotNull
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatus userStatus;

    @ToString.Exclude
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Credit credit;
}