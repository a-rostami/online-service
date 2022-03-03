package com.rostami.onlinehomeservices.dto.in.update;

import com.rostami.onlinehomeservices.dto.in.BaseUpdateDto;
import com.rostami.onlinehomeservices.model.Customer;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CustomerUpdateParam implements BaseUpdateDto<Customer> {

    @NotNull
    private Long id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @Email
    @NotNull
    private String email;

    @Override
    public Customer convertToDomain(Customer fetchedEntity) {
        fetchedEntity.setId(id);
        fetchedEntity.setFirstname(firstname);
        fetchedEntity.setLastname(lastname);
        fetchedEntity.setEmail(email);
        return fetchedEntity;
    }
}
