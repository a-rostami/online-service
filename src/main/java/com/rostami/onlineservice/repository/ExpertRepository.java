package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends BaseRepository<Expert, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Expert e SET e.password=:password WHERE e.id=:id")
    void changePassword(Long id, String password);
}
