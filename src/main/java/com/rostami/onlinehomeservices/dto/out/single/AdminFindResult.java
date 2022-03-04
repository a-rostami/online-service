package com.rostami.onlinehomeservices.dto.out.single;

import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.model.Admin;
import lombok.*;

import javax.validation.constraints.Email;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminFindResult that = (AdminFindResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
