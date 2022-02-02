package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.model.Customer;
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
    @Email
    private String email;

    @Override
    public CustomerFindResult convertToDto(Customer entity) {
        id = entity.getId();
        firstname = entity.getFirstname();
        lastname = entity.getLastname();
        email = entity.getEmail();
        return this;
    }
}
