package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Offer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long>, JpaSpecificationExecutor<Offer> {
    List<Offer> findAll(Specification<Offer> spec);

    List<Offer> findAll(Specification<Offer> spec, Sort sort);
}
