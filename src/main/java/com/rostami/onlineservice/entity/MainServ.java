package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Entity
public class MainServ extends BaseEntity {
    @Column(nullable = false)
    private String name;
}
