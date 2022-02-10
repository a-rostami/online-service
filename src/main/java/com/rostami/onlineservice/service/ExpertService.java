package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.in.update.api.PasswordUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.dto.out.single.SubServFindResult;
import com.rostami.onlineservice.exception.WrongPreviousPasswordException;
import com.rostami.onlineservice.model.*;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.ExpertRepository;
import com.rostami.onlineservice.service.base.UserService;
import com.rostami.onlineservice.service.registration.EmailTokenService;
import com.rostami.onlineservice.service.registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Set;

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

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(ExpertFindResult.builder().build());
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
        SubServFindResult subServFindResult = (SubServFindResult) subServService.get(subServId);
        SubServ subServ = SubServ.builder()
                .id(subServId)
                .basePrice(subServFindResult.getBasePrice())
                .name(subServFindResult.getName())
                .build();

        Expert expert = repository.findById(expertId).orElseThrow(
                () -> new EntityLoadException("There is no expert with this id"));

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
        Expert expert = repository.findById(id).orElseThrow(() -> new EntityLoadException("There is no expert with this id"));
        Double expertAveragePoint = expert.getAveragePoint();

        long numberOfAllExpertOpinions = opinionService.count((root, query, cb) -> cb.equal(root.get("expert"), expert));
        // mines one because just saved opinion will be count in specification query
        double sumOfExpertPoints = (numberOfAllExpertOpinions - 1) * expertAveragePoint;

        log.warn("expert average point : " + expertAveragePoint);
        log.warn("number of opinions : " + numberOfAllExpertOpinions);
        log.warn("sum of expert points : " + sumOfExpertPoints);

        // plus sum of points with given new point
        sumOfExpertPoints += point;

        log.warn("number of opinions : " + numberOfAllExpertOpinions);
        log.warn("sum of expert points : " + sumOfExpertPoints);

        log.warn("new average point : " + sumOfExpertPoints / numberOfAllExpertOpinions);
        expert.setAveragePoint(sumOfExpertPoints / numberOfAllExpertOpinions);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult depositToCredit(Long expertId, BigDecimal amount){
        Expert expert = repository.findById(expertId).orElseThrow(() ->
                new EntityLoadException("There Is No Expert With This ID!"));
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
            throw new EntityLoadException("There Is No Expert With This Id!");
        return CreateUpdateResult.builder().success(true).build();
    }

    @Transactional
    public CreateUpdateResult changePassword(PasswordUpdateParam param){
        Expert expert = repository.findByEmail(param.getEmail())
                .orElseThrow(() -> new EntityLoadException("There Is No Expert With This Email"));

        String expertPassword = expert.getPassword();

        if (!bCryptPasswordEncoder.matches(param.getPreviousPassword(), expertPassword))
            throw new WrongPreviousPasswordException("Previous Password Is Wrong !");

        expert.setPassword(param.getNewPassword());
        Expert saved = repository.save(expert);

        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }
}
