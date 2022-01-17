package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.entity.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@Builder
public class CustomerFindResult implements BaseOutDto<Customer, CustomerFindResult> {
    private String firstname;
    private String lastname;
    private String username;
    @Email
    private String email;

    @Override
    public CustomerFindResult convertToDto(Customer entity) {
        firstname = entity.getFirstname();
        lastname = entity.getLastname();
        username = entity.getUsername();
        email = entity.getEmail();
        return this;
    }
}
