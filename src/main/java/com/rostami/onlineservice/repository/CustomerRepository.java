package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
