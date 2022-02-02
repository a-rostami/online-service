package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.Admin;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateParam implements BaseInDto<Admin> {
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
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;

    @Override
    public Admin convertToDomain() {
        return Admin.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .password(password)
                .email(email)
                .build();
    }
}
