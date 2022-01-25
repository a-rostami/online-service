package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.enums.Role;
import com.rostami.onlineservice.entity.enums.UserStatus;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateParam implements BaseInDto<Customer> {
    // id can be null when there is no relation
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;

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
