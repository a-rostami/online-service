package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.abstracts.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class MainServ extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "mainServ", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<SubServ> subServs;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "name = " + getName() + ")";
    }
}
