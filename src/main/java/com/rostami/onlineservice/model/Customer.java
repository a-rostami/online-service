package com.rostami.onlineservice.model;

import com.rostami.onlineservice.model.base.User;
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