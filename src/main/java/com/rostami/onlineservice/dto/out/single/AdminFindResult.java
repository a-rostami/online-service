package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.model.Admin;
import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminFindResult implements BaseOutDto<Admin, AdminFindResult> {
    private Long id;
    private String firstname;
    private String lastname;
    @Email
    private String email;

    @Override
    public AdminFindResult convertToDto(Admin entity) {
        id = entity.getId();
        firstname = entity.getFirstname();
        lastname = entity.getLastname();
        email = entity.getEmail();
        return this;
    }
}
