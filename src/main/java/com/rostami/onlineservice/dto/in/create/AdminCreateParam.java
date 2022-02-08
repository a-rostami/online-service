package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.Admin;
import com.rostami.onlineservice.model.Credit;
import com.rostami.onlineservice.model.enums.UserStatus;
import com.rostami.onlineservice.service.bootstrap.SetupAuthorities;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import static com.rostami.onlineservice.model.security.enums.RoleEnum.ADMIN;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class AdminCreateParam implements BaseInDto<Admin> {
    private String firstname;
    private String lastname;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;


    @Override
    public Admin convertToDomain() {
        return Admin.builder()
                .firstname(firstname)
                .lastname(lastname)
                .password(password)
                .email(email)
                .roles(SetupAuthorities.SAVED_ROLES.stream()
                        .filter(role -> role.getRoleEnum().equals(ADMIN)).collect(Collectors.toSet()))
                .userStatus(UserStatus.PENDING)
                .isEnable(false)
                .isNonLocked(true)
                .credit(Credit.builder().balance(BigDecimal.valueOf(0)).build())
                .build();
    }
}
