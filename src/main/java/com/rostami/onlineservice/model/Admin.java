package com.rostami.onlineservice.model;

import com.rostami.onlineservice.model.base.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Entity
public class Admin extends User {
}
