package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.in.BaseDto;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.exception.DuplicatedEmailException;
import com.rostami.onlineservice.repository.CustomerRepository;
import com.rostami.onlineservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService extends BaseService<Customer, Long> {
    private final CustomerRepository repository;

    @PostConstruct
    public void init(){
        setJpaRepository(repository);
    }

    @Override
    public CreateUpdateResult saveOrUpdate(BaseDto<Customer> dto) {
        Customer entity = dto.convertToDomain();
        checkEmailExist(entity.getEmail(), entity.getId());
        Customer saved = repository.save(entity);
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    private void checkEmailExist(String email, Long id){
        List<Customer> byEmail = repository.findAll(((root, cq, cb) -> cb.equal(root.get("email"), email)));
        if (id == null  && !CollectionUtils.isEmpty(byEmail))
            throw new DuplicatedEmailException("Email Already Exist!");
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll(Specification<Customer> specification){
        return repository.findAll(specification);
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll(Specification<Customer> specification, Sort sort){
        return repository.findAll(specification, sort);
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll(Specification<Customer> specification, Pageable pageable){
        return repository.findAll(specification, pageable).getContent();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changePassword(Long id, String newPassword){
        repository.changePassword(id, newPassword);
    }
}
