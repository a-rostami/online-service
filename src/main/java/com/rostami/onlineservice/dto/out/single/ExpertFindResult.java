package com.rostami.onlineservice.dto.out.single;

import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.model.Expert;
import lombok.*;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<SubServFindResult> subServFindResults;

    @Override
    public ExpertFindResult convertToDto(Expert entity) {
        id = entity.getId();
        firstname = entity.getFirstname();
        lastname = entity.getLastname();
        username = entity.getUsername();
        email = entity.getEmail();
        subServFindResults = entity.getSubServs().stream().map(subServ ->
                new SubServFindResult().convertToDto(subServ)).collect(Collectors.toList());
        return this;
    }
}
