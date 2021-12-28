package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.abstracts.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@Entity
public class Expert extends User {
    @Column( columnDefinition = "BLOB", nullable = false)
    @Lob
    private byte[] avatar;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "expert", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Credit credit;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Opinion> opinions;
}
