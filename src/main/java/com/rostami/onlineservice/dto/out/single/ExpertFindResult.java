package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.entity.Expert;
import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertFindResult implements BaseOutDto<Expert, ExpertFindResult> {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    @Email
    private String email;

    @Override
    public ExpertFindResult convertToDto(Expert entity) {
        id = entity.getId();
        firstname = entity.getFirstname();
        lastname = entity.getLastname();
        username = entity.getUsername();
        email = entity.getEmail();
        return this;
    }
}
