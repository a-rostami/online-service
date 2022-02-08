package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.Credit;
import com.rostami.onlineservice.model.Expert;
import com.rostami.onlineservice.model.enums.UserStatus;
import com.rostami.onlineservice.service.bootstrap.SetupAuthorities;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import static com.rostami.onlineservice.model.security.enums.RoleEnum.EXPERT;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ExpertCreateParam implements BaseInDto<Expert> {
    private String firstname;
    private String lastname;
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
                .password(password)
                .email(email)
                .avatar(avatar.getBytes())
                .roles(SetupAuthorities.SAVED_ROLES.stream()
                        .filter(role -> role.getRoleEnum().equals(EXPERT)).collect(Collectors.toSet()))
                .userStatus(UserStatus.PENDING)
                .isEnable(false)
                .isNonLocked(false)
                .credit(Credit.builder().balance(BigDecimal.valueOf(0)).build())
                .build();
    }
}
