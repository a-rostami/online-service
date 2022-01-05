package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.Opinion;
import com.rostami.onlineservice.exception.DuplicateEmailException;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExpertService extends BaseService<Expert, Long> {
    private final ExpertRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(Expert entity) {
        checkEmailExist(entity.getEmail(), entity.getId());
        repository.save(entity);
    }

    private void checkEmailExist(String email, Long id){
        List<Expert> byEmail = repository.findAll(((root, cq, cb) -> cb.equal(root.get("email"), email)));
        if (id == null && byEmail.size() > 0)
            throw new DuplicateEmailException("Email Exist!");
    }

    public Double getAveragePoint(Expert expert){
        Set<Opinion> opinions = expert.getOpinions();
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
