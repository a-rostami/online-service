package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Customer c SET c.password=:password WHERE c.id=:id")
    void changePassword(Long id, String password);

}
