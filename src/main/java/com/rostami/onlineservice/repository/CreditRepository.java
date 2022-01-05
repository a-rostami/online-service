package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Credit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long>, JpaSpecificationExecutor<Credit> {

    List<Credit> findAll(Specification<Credit> spec, Sort sort);

    List<Credit> findAll(Specification<Credit> spec);
}
