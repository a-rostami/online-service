package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    List<Customer> findAll(Specification<Customer> spec);

    List<Customer> findAll(Specification<Customer> spec, Sort sort);

    Page<Customer> findAll(Specification<Customer> spec, Pageable pageable);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Customer c SET c.password=:password WHERE c.id=:id")
    void changePassword(Long id, String password);

}
