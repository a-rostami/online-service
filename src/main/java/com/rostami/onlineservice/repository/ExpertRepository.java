package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Expert;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {

    List<Expert> findAll(Specification<Expert> spec);

    List<Expert> findAll(Specification<Expert> spec, Sort sort);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Expert e SET e.password=:password WHERE e.id=:id")
    void changePassword(Long id, String password);
}
