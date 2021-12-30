package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.exception.DuplicateEmailException;
import com.rostami.onlineservice.repository.ExpertRepository;
import com.rostami.onlineservice.service.abstracts.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ExpertService extends BaseService<Expert, Long> {
    private final ExpertRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }

    @Override
    public void save(Expert entity) {
        checkEmailExist(entity.getEmail());
        repository.save(entity);
    }

    private void checkEmailExist(String email){
        Expert byEmail = repository.findByEmail(email);
        if (byEmail != null)
            throw new DuplicateEmailException("Email Exist!");
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
