package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.model.Expert;
import com.rostami.onlineservice.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpertRepository extends BaseRepository<Expert, Long> {

    @Query("SELECT e FROM Expert e JOIN e.subServs s WHERE s.id = :id")
    List<Expert> joinExpertSubServ(Long id);
}
