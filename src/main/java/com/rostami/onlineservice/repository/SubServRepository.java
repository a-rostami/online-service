package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.SubServ;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SubServRepository extends JpaRepository<SubServ, Long>, JpaSpecificationExecutor<SubServ> {
    List<SubServ> findAll(Specification<SubServ> spec);

    List<SubServ> findAll(Specification<SubServ> spec, Sort sort);
}
