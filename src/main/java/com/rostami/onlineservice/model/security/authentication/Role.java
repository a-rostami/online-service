package com.rostami.onlineservice.model.security.authentication;

import com.rostami.onlineservice.model.base.BaseEntity;
import com.rostami.onlineservice.model.security.enums.RoleEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@Entity
public class Role extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name="role_permission",
            joinColumns=@JoinColumn(name="role_id"),
            inverseJoinColumns=@JoinColumn(name="permission_id")
    )
    private Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities(Set<Permission> permissions){
        Set<SimpleGrantedAuthority> authorities = permissions.stream().map(permission ->
                new SimpleGrantedAuthority(permission.getPermissionEnum().getName())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleEnum.name()));
        return authorities;
    }

}