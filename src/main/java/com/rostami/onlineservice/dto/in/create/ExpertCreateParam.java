package com.rostami.onlineservice.dto.in.create;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.enums.Role;
import com.rostami.onlineservice.entity.enums.UserStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertCreateParam implements BaseInDto<Expert> {
    // id can be null when there is no relation
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private MultipartFile avatar;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*?[0-9]).{8,}$")
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
                .role(Role.CUSTOMER)
                .userStatus(UserStatus.NEW)
                .build();
    }
}
