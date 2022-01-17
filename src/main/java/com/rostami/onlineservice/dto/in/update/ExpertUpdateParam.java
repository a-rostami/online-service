package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseDto;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.Expert;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
public class ExpertUpdateParam implements BaseDto<Expert> {
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
