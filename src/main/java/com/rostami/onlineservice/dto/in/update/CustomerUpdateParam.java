package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.Customer;
import com.rostami.onlineservice.model.enums.UserStatus;
import com.rostami.onlineservice.service.CustomerService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CustomerUpdateParam implements BaseInDto<Customer> {

    @NotNull
    private Long id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @Email
    @NotNull
    private String email;
    @NotNull
    private UserStatus userStatus;

    @Override
    public Customer convertToDomain() {
        return Customer.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .userStatus(userStatus)
                .build();
    }
}
