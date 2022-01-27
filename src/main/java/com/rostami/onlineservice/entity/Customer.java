package com.rostami.onlineservice.entity;

import com.rostami.onlineservice.entity.base.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Entity
public class Customer extends User {}