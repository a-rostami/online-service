package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.Opinion;
import com.rostami.onlineservice.exception.DuplicateEmailException;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.ExpertRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

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
        Expert byEmail = repository.findByEmail(email);
        if (byEmail != null && !byEmail.getId().equals(id))
            throw new DuplicateEmailException("Email Exist!");
    }

    public Double getAveragePoint(Long id){
        Expert expert = repository.findById(id).orElseThrow(() ->new EntityLoadException("Expert Was Not Found."));
        List<Opinion> opinions = expert.getOpinions();
        return opinions.stream().mapToInt(Opinion::getRate).average().orElse(0);
    }

    public Expert findByUsername(String username){
        return repository.findByUsername(username);
    }

    public Expert findByUserNameAndPassword(String username, String password){
        return repository.findByUsernameAndPassword(username, password);
    }

    public Expert findByEmail(String email){
        return repository.findByEmail(email);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changePassword(Long id, String newPassword){
        repository.changePassword(id, newPassword);
    }
}
