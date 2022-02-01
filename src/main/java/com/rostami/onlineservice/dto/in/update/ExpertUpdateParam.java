package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.model.Expert;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertUpdateParam implements BaseInDto<Expert> {
    @NotNull
    private Long id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String username;
    @NotNull
    private MultipartFile avatar;
    @Email
    @NotNull
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    @NotNull
    private String password;

    @SneakyThrows
    @Override
    public Expert convertToDomain() {
        return Expert.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(password)
                .email(email)
                .avatar(avatar.getBytes())
                .build();
    }
}
