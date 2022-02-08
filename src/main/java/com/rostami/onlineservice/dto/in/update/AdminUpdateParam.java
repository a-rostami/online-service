package com.rostami.onlineservice.dto.in.update;

import com.rostami.onlineservice.dto.in.BaseUpdateDto;
import com.rostami.onlineservice.model.Admin;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateParam implements BaseUpdateDto<Admin> {
    @NotNull
    private Long id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @Email
    @NotNull
    private String email;

    @Override
    public Admin convertToDomain(Admin fetchedEntity) {
        fetchedEntity.setId(id);
        fetchedEntity.setFirstname(firstname);
        fetchedEntity.setLastname(lastname);
        fetchedEntity.setEmail(email);
        return fetchedEntity;
    }
}
