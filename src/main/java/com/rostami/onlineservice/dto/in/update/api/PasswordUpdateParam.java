package com.rostami.onlineservice.dto.in.update.api;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateParam {

    @NotNull
    private String email;

    @NotNull
    private String previousPassword;

    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String newPassword;
}
