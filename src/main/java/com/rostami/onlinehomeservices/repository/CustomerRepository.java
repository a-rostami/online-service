package com.rostami.onlinehomeservices.repository;

import com.rostami.onlinehomeservices.model.Customer;
import com.rostami.onlinehomeservices.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends BaseRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    @Query("UPDATE Customer c SET c.isEnable = true WHERE c.email = ?1")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    int enableCustomer(String email);
}