package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Opinion;
import com.rostami.onlineservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionRepository extends BaseRepository<Opinion, Long> {
}
