package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseDto;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.enums.Role;
import com.rostami.onlineservice.entity.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@Builder
public class CustomerCreateParam implements BaseDto<Customer> {
    private String firstname;
    private String lastname;
    private String username;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*?[0-9]).{8,}$")
    private String password;
    private Role role;

    @Override
    public Customer convertToDomain() {
        return Customer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(password)
                .email(email)
                .role(Role.CUSTOMER)
                .userStatus(UserStatus.NEW)
                .build();
    }
}
