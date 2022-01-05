package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Opinion;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OpinionRepository extends JpaRepository<Opinion, Long>, JpaSpecificationExecutor<Opinion> {
    List<Opinion> findAll(Specification<Opinion> spec, Sort sort);

    List<Opinion> findAll(Specification<Opinion> spec);
}
