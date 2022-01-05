package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.MainServ;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MainServRepository extends JpaRepository<MainServ, Long>, JpaSpecificationExecutor<MainServ> {
    List<MainServ> findAll(Specification<MainServ> spec, Sort sort);

    List<MainServ> findAll(Specification<MainServ> spec);
}
