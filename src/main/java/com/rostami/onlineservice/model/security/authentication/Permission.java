package com.rostami.onlineservice.model.security.authentication;

import com.rostami.onlineservice.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@Entity
public class Permission extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name="role_permission",
            joinColumns=@JoinColumn(name="permission_id"),
            inverseJoinColumns=@JoinColumn(name="role_id")
    )
    private Set<Role> roles;
}