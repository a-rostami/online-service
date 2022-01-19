package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.entity.Customer;
import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFindResult implements BaseOutDto<Customer, CustomerFindResult> {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    @Email
    private String email;

    @Override
    public CustomerFindResult convertToDto(Customer entity) {
        id = entity.getId();
        firstname = entity.getFirstname();
        lastname = entity.getLastname();
        username = entity.getUsername();
        email = entity.getEmail();
        return this;
    }
}
