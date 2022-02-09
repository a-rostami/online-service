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
    @Email
    private String email;
    private Double averagePoint;
    private List<SubServFindResult> subServFindResults;

    @Override
    public ExpertFindResult convertToDto(Expert entity) {
        id = entity.getId();
        firstname = entity.getFirstname();
        lastname = entity.getLastname();
        email = entity.getEmail();
        averagePoint = entity.getAveragePoint();
        subServFindResults = entity.getSubServs().stream().map(subServ ->
                SubServFindResult.builder().build().convertToDto(subServ)).collect(Collectors.toList());
        return this;
    }
}
