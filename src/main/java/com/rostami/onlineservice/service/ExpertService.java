package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.dto.in.update.ExpertUpdateParam;
import com.rostami.onlineservice.dto.in.update.SubServUpdateParam;
import com.rostami.onlineservice.dto.out.BaseOutDto;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.CreditFindResult;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.dto.out.single.SubServFindResult;
import com.rostami.onlineservice.entity.*;
import com.rostami.onlineservice.exception.DuplicatedEmailException;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.ExpertRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertService extends BaseService<Expert, Long> {
    private final ExpertRepository repository;
    private final OpinionService opinionService;
    private final SubServService subServService;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
        setBaseOutDto(ExpertFindResult.builder().build());
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

    public Double getAveragePoint(Expert expert){
        List<Opinion> opinions = opinionService.findAll(((root, query, cb) -> cb.equal(root.get("expert"), expert)));
        return opinions.stream().mapToInt(Opinion::getRate).average().orElse(0);
    }

    @Transactional(readOnly = true)
    public List<Expert> findAll(Specification<Expert> specification){
        return repository.findAll(specification);
    }

    @Transactional(readOnly = true)
    public List<Expert> findAll(Specification<Expert> specification, Sort sort){
        return repository.findAll(specification, sort);
    }

    @Transactional(readOnly = true)
    public List<Expert> findAll(Specification<Expert> specification, Pageable pageable){
        return repository.findAll(specification, pageable).getContent();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changePassword(Long id, String newPassword){
        repository.changePassword(id, newPassword);
    }
}
