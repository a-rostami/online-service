package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Entity
public class Expert extends User {
    @Lob
    @Column( columnDefinition = "BLOB", nullable = false)
    private byte[] avatar;

    @ToString.Exclude
    @OneToOne(mappedBy = "expert", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Credit credit;
}