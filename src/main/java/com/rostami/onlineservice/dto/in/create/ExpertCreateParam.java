package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.Credit;
import com.rostami.onlineservice.model.Expert;
import com.rostami.onlineservice.model.enums.Role;
import com.rostami.onlineservice.model.enums.UserStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertCreateParam implements BaseInDto<Expert> {
    private String firstname;
    private String lastname;
    private String username;
    private MultipartFile avatar;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;

    @SneakyThrows
    @Override
    public Expert convertToDomain() {
        return Expert.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(password)
                .email(email)
                .avatar(avatar.getBytes())
                .role(Role.EXPERT)
                .userStatus(UserStatus.NEW)
                .credit(Credit.builder().balance(BigDecimal.valueOf(0)).build())
                .build();
    }
}
