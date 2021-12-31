package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ExpertRepository extends JpaRepository<Expert, Long> {
    @Query("SELECT e FROM Expert e WHERE e.email=:email")
    Expert findByEmail(String email);

    Expert findByUsername(String username);

    Expert findByUsernameAndPassword(String username, String password);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Expert e SET e.password=:password WHERE e.id=:id")
    void changePassword(Long id, String password);

    @Query("SELECT e FROM Expert e WHERE e.email=:email")
    Expert findBySubServ(String email);
}
