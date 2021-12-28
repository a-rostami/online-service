package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.abstracts.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@Entity
public class MainServ extends BaseEntity {
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<SubServ> subServs;
}
