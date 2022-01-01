package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface CustomerRepository extends JpaRepository<Customer, Long>{

    Customer findByEmail(String email);

    Customer findByUsername(String username);

    Customer findByUsernameAndPassword(String username, String password);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Customer c SET c.password=:password WHERE c.id=:id")
    void changePassword(Long id, String password);

}
