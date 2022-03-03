package com.rostami.onlinehomeservices.dto.out.single;

import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.model.Admin;
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
