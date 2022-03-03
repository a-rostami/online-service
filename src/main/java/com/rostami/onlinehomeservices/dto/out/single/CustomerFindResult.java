package com.rostami.onlinehomeservices.dto.out.single;

import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.model.Customer;
import com.rostami.onlinehomeservices.model.enums.UserStatus;
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
    private UserStatus userStatus;

    @Override
    public CustomerFindResult convertToDto(Customer entity) {
        id = entity.getId();
        firstname = entity.getFirstname();
        lastname = entity.getLastname();
        email = entity.getEmail();
        userStatus = entity.getUserStatus();
        return this;
    }
}
