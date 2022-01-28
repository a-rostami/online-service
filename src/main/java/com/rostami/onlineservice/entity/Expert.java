package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name="expert_subServ",
            joinColumns=@JoinColumn(name="expert_id"),
            inverseJoinColumns=@JoinColumn(name="subServ_id")
    )
    private List<SubServ> subServs;
}