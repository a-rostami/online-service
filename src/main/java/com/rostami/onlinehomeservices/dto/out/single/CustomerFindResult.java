package com.rostami.onlinehomeservices.dto.out.single;

import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.model.Customer;
import com.rostami.onlinehomeservices.model.enums.UserStatus;
import lombok.*;

import javax.validation.constraints.Email;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerFindResult that = (CustomerFindResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
