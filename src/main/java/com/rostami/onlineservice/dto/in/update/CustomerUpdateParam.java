package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
public class CustomerUpdateParam implements BaseInDto<Customer> {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*?[0-9]).{8,}$")
    private String password;

    @Override
    public Customer convertToDomain() {
        return Customer.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
