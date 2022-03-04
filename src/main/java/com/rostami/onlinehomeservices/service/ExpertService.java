package com.rostami.onlinehomeservices.service;

import com.rostami.onlinehomeservices.dto.in.update.api.PasswordUpdateParam;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.ExpertFindResult;
import com.rostami.onlinehomeservices.dto.out.single.SubServFindResult;
import com.rostami.onlinehomeservices.exception.WrongPreviousPasswordException;
import com.rostami.onlinehomeservices.model.*;
import com.rostami.onlinehomeservices.exception.EntityLoadException;
import com.rostami.onlinehomeservices.repository.ExpertRepository;
import com.rostami.onlinehomeservices.repository.impl.UpdateRepositoryImpl;
import com.rostami.onlinehomeservices.service.base.UserService;
import com.rostami.onlinehomeservices.service.registration.EmailTokenService;
import com.rostami.onlinehomeservices.service.registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Set;

import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpertService extends UserService<Expert, Long, ExpertFindResult> {
    private final ExpertRepository repository;
    private final OpinionService opinionService;
    private final AdService adService;
    private final SubServService subServService;
    private final RegistrationService registrationService;
    private final EmailTokenService emailTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UpdateRepositoryImpl<Expert, Long> updateRepository;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(ExpertFindResult.builder().build());
        setUpdateRepositoryImpl(updateRepository);
        setRegistrationService(registrationService);
        setEmailTokenService(emailTokenService);
    }

    @Transactional
    public boolean enableCustomer(String email){
        int result = repository.enableExpert(email);
        return result > 0;
    }

    @Transactional
    public CreateUpdateResult addSubServ(Long expertId, Long subServId){
        SubServFindResult subServFindResult = (SubServFindResult) subServService.findById(subServId);
        SubServ subServ = SubServ.builder()
                .id(subServId)
                .basePrice(subServFindResult.getBasePrice())
                .name(subServFindResult.getName())
                .build();

        Expert expert = repository.findById(expertId).orElseThrow(
                () -> new EntityLoadException(ENTITY_ID_LOAD_MESSAGE));

        Set<SubServ> subServs = expert.getSubServs();
        subServs.add(subServ);
        expert.setSubServs(subServs);

        Expert saved = repository.save(expert);
        return CreateUpdateResult.builder().success(true).id(saved.getId()).build();
    }

    @Transactional
    public void updateAveragePoint(double newAveragePoint, Long expertId){
        repository.updateAveragePoint(newAveragePoint, expertId);
    }


    @Transactional
    /* Formula :
      ( Count Of numbers * Average ) + New Point / ( Count Of Numbers + 1 )
    */
    public void addNewPointToExpertAveragePoint(Long id, double point){
        Expert expert = repository.findById(id)
                .orElseThrow(() -> new EntityLoadException(ENTITY_ID_LOAD_MESSAGE));
        Double expertAveragePoint = expert.getAveragePoint();

        long numberOfAllExpertOpinions = opinionService.count((root, query, cb) -> cb.equal(root.get("expert"), expert));
        // mines one because just saved opinion will be count in specification query
        double sumOfExpertPoints = (numberOfAllExpertOpinions - 1) * expertAveragePoint;

        // plus sum of points with given new point
        sumOfExpertPoints += point;
        expert.setAveragePoint(sumOfExpertPoints / numberOfAllExpertOpinions);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult depositToCredit(Long expertId, BigDecimal amount){
        Expert expert = repository.findById(expertId).orElseThrow(() ->
                new EntityLoadException(ENTITY_ID_LOAD_MESSAGE));
        return super.depositToCredit(expert, amount);
    }

    @Transactional
    public long getNumberOfDoneAds(Long expertId){
        Expert expert = Expert.builder().id(expertId).build();
        return adService.count((root, query, cb) -> cb.equal(root.get("chosenExpert"), expert));
    }

    @Transactional
    public CreateUpdateResult unlockExpert(String email){
        int countOfChangedRows = repository.unlockExpert(email);
        if (countOfChangedRows < 1)
            throw new EntityLoadException(ENTITY_ID_LOAD_MESSAGE);
        return CreateUpdateResult.builder().success(true).build();
    }

    @Transactional
    public CreateUpdateResult changePassword(PasswordUpdateParam param){
        Expert expert = repository.findByEmail(param.getEmail())
                .orElseThrow(() -> new EntityLoadException(ENTITY_EMAIL_LOAD_MESSAGE));

        String expertPassword = expert.getPassword();

        if (!bCryptPasswordEncoder.matches(param.getPreviousPassword(), expertPassword))
            throw new WrongPreviousPasswordException(WRONG_PREVIOUS_PASSWORD_MESSAGE);

        expert.setPassword(param.getNewPassword());
        Expert saved = repository.save(expert);

        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }
}
