package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private byte[] avatar;

    @ToString.Exclude
    @OneToOne(mappedBy = "expert", cascade = {CascadeType.MERGE})
    private Credit credit;
}