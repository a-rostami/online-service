package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends BaseRepository<Customer, Long> {
}