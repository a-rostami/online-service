package com.rostami.onlinehomeservices.repository;

import com.rostami.onlinehomeservices.model.Expert;
import com.rostami.onlinehomeservices.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExpertRepository extends BaseRepository<Expert, Long> {
     Optional<Expert> findByEmail(String email);

     @Query("UPDATE Expert e SET e.isEnable = true WHERE e.email = ?1")
     @Modifying(clearAutomatically = true, flushAutomatically = true)
     int enableExpert(String email);

     @Modifying(flushAutomatically = true, clearAutomatically = true)
     @Query("UPDATE Expert e SET e.isNonLocked = true WHERE e.email = ?1")
     int unlockExpert(String email);

     @Modifying(flushAutomatically = true, clearAutomatically = true)
     @Query("UPDATE Expert e SET e.averagePoint = ?1 WHERE e.id = ?2")
     int updateAveragePoint(Double averagePoint, Long id);

}