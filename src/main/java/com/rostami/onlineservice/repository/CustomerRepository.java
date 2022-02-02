package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.model.Customer;
import com.rostami.onlineservice.repository.base.BaseRepository;

import java.util.Optional;

public interface CustomerRepository extends BaseRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}