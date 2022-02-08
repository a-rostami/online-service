package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.model.Expert;
import com.rostami.onlineservice.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExpertRepository extends BaseRepository<Expert, Long> {
     Optional<Expert> findByEmail(String email);

     @Query("UPDATE Expert e SET e.isEnable = true WHERE e.email = ?1")
     @Modifying(clearAutomatically = true, flushAutomatically = true)
     int enableCustomer(String email);

     @Modifying(flushAutomatically = true, clearAutomatically = true)
     @Query("UPDATE Expert e SET e.isNonLocked = true WHERE e.id = ?1")
     int unlockExpert(Long id);

}
