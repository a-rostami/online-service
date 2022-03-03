package com.rostami.onlinehomeservices.dto.out.single;

import com.rostami.onlinehomeservices.dto.out.BaseOutDto;
import com.rostami.onlinehomeservices.model.Expert;
import lombok.*;

import javax.validation.constraints.Email;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Math.round;

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
    private Set<SubServFindResult> subServFindResults;

    @Override
    public ExpertFindResult convertToDto(Expert entity) {
        id = entity.getId();
        firstname = entity.getFirstname();
        lastname = entity.getLastname();
        email = entity.getEmail();
        averagePoint = getRoundTwoDecimal(entity.getAveragePoint());
        subServFindResults = entity.getSubServs().stream().map(subServ ->
                SubServFindResult.builder().build().convertToDto(subServ)).collect(Collectors.toSet());
        return this;
    }

    private double getRoundTwoDecimal(double number){
        number *= 100;
        number = round(number);
        return number / 100;
    }
}
