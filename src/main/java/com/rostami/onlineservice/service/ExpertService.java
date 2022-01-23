package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.dto.in.update.SubServUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.AdFindResult;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.entity.*;
import com.rostami.onlineservice.exception.DuplicatedEmailException;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.ExpertRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Lazy
public class ExpertService extends BaseService<Expert, Long> {
    private final ExpertRepository repository;
    private final OpinionService opinionService;
    private final AdService adService;

    @PostConstruct
    public void init(){
        setRepository(repository);
        setBaseOutDto(ExpertFindResult.builder().build());
    }

    @Transactional
    public List<AdFindResult> findAdsRelatedToExpertSubServ(Long expertId){
        Expert expert = repository.findById(expertId).orElseThrow(
                () -> new EntityLoadException("There is no expert with this id"));
        List<SubServ> subServs = expert.getSubServs();

        List<Ad> results = adService.findAll((root, query, cb) -> root.get("subServ").in(subServs));
        return results.stream().map((ad) -> AdFindResult.builder().build()
                .convertToDto(ad)).collect(Collectors.toList());
    }

    @Transactional
    public CreateUpdateResult addSubServ(Long id, SubServUpdateParam subServParam){
        SubServ subServ = subServParam.convertToDomain();
        Expert expert = repository.findById(id).orElseThrow(
                () -> new EntityLoadException("There is no expert with this id"));

        List<SubServ> subServs = expert.getSubServs();
        subServs.add(subServ);
        expert.setSubServs(subServs);

        Expert saved = repository.save(expert);
        return CreateUpdateResult.builder().success(true).id(saved.getId()).build();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateUpdateResult saveOrUpdate(BaseInDto<Expert> dto) {
        Expert entity = dto.convertToDomain();
        checkEmailExist(entity.getEmail(), entity.getId());
        Expert saved = repository.save(entity);
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    private void checkEmailExist(String email, Long id){
        List<Expert> byEmail = repository.findAll(((root, cq, cb) -> cb.equal(root.get("email"), email)));
        if (id == null && byEmail.size() > 0)
            throw new DuplicatedEmailException("Email Already Exist!");
    }

    public Double getAveragePoint(Long id){
        Expert expert = repository.findById(id).orElseThrow(() -> new EntityLoadException("There is no expert with this id"));
        List<Opinion> opinions = opinionService.findAll(((root, query, cb) -> cb.equal(root.get("expert"), expert)));
        return opinions.stream().mapToInt(Opinion::getRate).average().orElse(0);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changePassword(Long id, String newPassword){
        repository.changePassword(id, newPassword);
    }
}
