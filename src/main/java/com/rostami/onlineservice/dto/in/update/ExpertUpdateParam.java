package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseUpdateDto;
import com.rostami.onlineservice.model.Expert;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertUpdateParam implements BaseUpdateDto<Expert> {
    @NotNull
    private Long id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private MultipartFile avatar;
    @Email
    @NotNull
    private String email;

    @SneakyThrows
    @Override
    public Expert convertToDomain(Expert fetchedEntity) {
        fetchedEntity.setId(id);
        fetchedEntity.setFirstname(firstname);
        fetchedEntity.setLastname(lastname);
        fetchedEntity.setEmail(email);
        fetchedEntity.setAvatar(avatar.getBytes());
        return fetchedEntity;
    }
}
