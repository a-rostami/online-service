package com.rostami.onlineservice.entity.abstracts;

import com.rostami.onlineservice.entity.enums.Role;
import com.rostami.onlineservice.entity.enums.UserStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@MappedSuperclass
@Entity
public abstract class User extends BaseEntity {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}