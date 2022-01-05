package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Ad;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long>, JpaSpecificationExecutor<Ad>{

    List<Ad> findAll(Specification<Ad> spec);

    List<Ad> findAll(Specification<Ad> spec, Sort sort);

    List<Ad> findAll(Sort sort);
}
