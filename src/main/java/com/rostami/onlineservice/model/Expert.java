package com.rostami.onlineservice.model;

import com.rostami.onlineservice.model.base.User;
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

    @Column(precision = 2, scale = 2)
    @NotNull
    @Builder.Default
    private Double averagePoint = 0.0;

    @Lob
    @Column( columnDefinition = "BLOB", nullable = false)
    @NotNull
    private byte[] avatar;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name="expert_subServ",
            joinColumns=@JoinColumn(name="expert_id"),
            inverseJoinColumns=@JoinColumn(name="subServ_id")
    )
    private List<SubServ> subServs;
}