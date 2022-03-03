package com.rostami.onlinehomeservices.model;

import com.rostami.onlinehomeservices.model.base.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Entity
public class Customer extends User {

}