package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.email=:email")
    Customer findByEmail(String email);

    Customer findByUsername(String username);

    Customer findByUsernameAndPassword(String username, String password);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Customer c SET c.password=:password WHERE c.id=:id")
    void changePassword(Long id, String password);

}
