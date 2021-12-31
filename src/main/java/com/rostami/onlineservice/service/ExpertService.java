package com.rostami.onlineservice.service;

import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.Opinion;
import com.rostami.onlineservice.exception.DuplicateEmailException;
import com.rostami.onlineservice.repository.ExpertRepository;
import com.rostami.onlineservice.service.abstracts.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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

    public Double getAveragePoint(Long id){
        Expert expert = new Expert();
        Optional<Expert> byId = repository.findById(id);
        if (byId.isPresent())
            expert = byId.get();
        List<Opinion> opinions = expert.getOpinions();
        AtomicReference<Double> average = new AtomicReference<>(0.0);
        opinions.forEach(opinion -> average.updateAndGet(v -> v + opinion.getRate()));
        return average.get() / opinions.size();
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
